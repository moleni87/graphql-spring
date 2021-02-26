package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import kr.inplat.graphqltest.domain.Book;
import kr.inplat.graphqltest.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
