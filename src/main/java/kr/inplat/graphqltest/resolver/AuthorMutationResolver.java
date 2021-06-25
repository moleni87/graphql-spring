package kr.inplat.graphqltest.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import kr.inplat.graphqltest.domain.Author;
import kr.inplat.graphqltest.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMutationResolver implements GraphQLMutationResolver {

    private final AuthorRepository authorRepository;

    public AuthorMutationResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String firstName, String lastName) {
        return authorRepository.save(new Author(firstName, lastName));
    }

    // TODO
}
