package kr.inplat.graphqltest.resolver;

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
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import java.util.List;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
public class BookGraphQLContextBuilder implements GraphQLServletContextBuilder {

  Log log = LogFactory.getLog(this.getClass());

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
    DataLoader<Long, List<Book>> bookDataLoader = new DataLoader(
        authorIds -> supplyAsync(() -> {
          log.info(authorIds);
          return bookRepository.findByAuthorIdIn(authorIds);
        })
    );
    dataLoaderRegistry.register("bookDataLoader", bookDataLoader);
    return dataLoaderRegistry;
  }
}
