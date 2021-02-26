package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import kr.inplat.graphqltest.domain.Author;
import kr.inplat.graphqltest.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }
}
