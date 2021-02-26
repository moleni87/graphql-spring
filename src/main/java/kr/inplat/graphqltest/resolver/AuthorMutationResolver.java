package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import kr.inplat.graphqltest.domain.Author;
import kr.inplat.graphqltest.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private AuthorRepository authorRepository;

    public Author createAuthor(String firstName, String lastName) {
        return authorRepository.save(new Author(firstName, lastName));
    }
}
