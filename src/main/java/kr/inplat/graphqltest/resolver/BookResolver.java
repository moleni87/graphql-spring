package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import kr.inplat.graphqltest.domain.Author;
import kr.inplat.graphqltest.domain.Book;
import kr.inplat.graphqltest.domain.repository.AuthorRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookResolver implements GraphQLResolver<Book> {

    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthor(Book book) {
        log.info(book.getId());
        return authorRepository.getById(book.getAuthorId()).orElse(null);
    }
}
