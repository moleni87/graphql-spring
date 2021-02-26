package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import kr.inplat.graphqltest.domain.Book;
import kr.inplat.graphqltest.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(String title, String isbn, Integer pageCount, Long authorId) {
        return bookRepository.save(new Book(title, isbn, pageCount, authorId));
    }
}
