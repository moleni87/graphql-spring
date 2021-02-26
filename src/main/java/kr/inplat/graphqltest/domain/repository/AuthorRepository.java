package kr.inplat.graphqltest.domain.repository;

import kr.inplat.graphqltest.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAll();

    Optional<Author> getById(Long id);
}
