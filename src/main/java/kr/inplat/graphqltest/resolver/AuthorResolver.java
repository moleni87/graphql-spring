package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import kr.inplat.graphqltest.domain.Author;
import kr.inplat.graphqltest.domain.Book;
import kr.inplat.graphqltest.domain.repository.AuthorRepository;
import kr.inplat.graphqltest.domain.repository.BookRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AuthorResolver implements GraphQLResolver<Author> {

    Log log = LogFactory.getLog(this.getClass());

    public CompletableFuture<List<Book>> getBooks(Author author, DataFetchingEnvironment environment) {
        DataLoaderRegistry registry = environment.getDataLoaderRegistry();
        DataLoader<Long, List<Book>> bookDataLoader = registry.getDataLoader("bookDataLoader");
        if (bookDataLoader != null) {
            log.info(environment.getArguments());
            log.info(environment.getSelectionSet());
            return bookDataLoader.load(author.getId());
        }
        throw new IllegalStateException("No book data loader found");
    }
}
