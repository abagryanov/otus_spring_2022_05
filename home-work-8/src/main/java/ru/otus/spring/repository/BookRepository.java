package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

@Component
public interface BookRepository extends Repository<Book, String>, BookCustomRepository {
    @Query(value = "{'_id': :#{#id} }", fields = "{'comments':  0}")
    Optional<Book> findByIdWithoutComments(@Param("id") String id);

    @Query(value = "{'name': :#{#name} }", fields = "{'comments':  0}")
    List<Book> findByNameWithoutComments(@Param("name") String name);

    @Query(value = "{}", fields = "{'comments':  0}")
    List<Book> findAllWithoutComments();

    @DeleteQuery(value = "{'_id': :#{#book.id} }")
    void delete(Book book);

    Book save(Book book);
}
