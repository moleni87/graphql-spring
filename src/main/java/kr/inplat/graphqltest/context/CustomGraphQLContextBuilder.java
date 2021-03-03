package kr.inplat.graphqltest.context;

import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import kr.inplat.graphqltest.domain.Book;
import kr.inplat.graphqltest.domain.repository.BookRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

    Log log = LogFactory.getLog(this.getClass());

    private static final Executor priceExecutor = new DelegatingSecurityContextExecutorService(Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    @Autowired
    private BookRepository bookRepository;

    @Override
    public GraphQLContext build(HttpServletRequest req, HttpServletResponse response) {
        return DefaultGraphQLServletContext.createServletContext(buildDataLoaderRegistry(), null)
                .with(req).with(response)
                .build();
    }

    @Override
    public GraphQLContext build() {
        return new DefaultGraphQLContext(buildDataLoaderRegistry(), null);
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest request) {
        return DefaultGraphQLWebSocketContext.createWebSocketContext(buildDataLoaderRegistry(), null)
                .with(session)
                .with(request).build();
    }

    private DataLoaderRegistry buildDataLoaderRegistry() {
        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
        MappedBatchLoader<Long, List<Book>> bookDataLoader = keys -> supplyAsync(
                () -> bookRepository.findByAuthorIdIn(keys).stream().collect(Collectors.groupingBy(Book::getAuthorId))
        , priceExecutor);
        log.info(priceExecutor);
        dataLoaderRegistry.register("bookDataLoader", DataLoader.newMappedDataLoader(bookDataLoader, DataLoaderOptions.newOptions().setMaxBatchSize(20)));
        return dataLoaderRegistry;
    }
}
