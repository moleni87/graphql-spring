package kr.inplat.graphqltest.domain.repository;

import kr.inplat.graphqltest.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();

    List<Book> findByAuthorIdIn(Set<Long> authorIds);
}
