package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookCustomRepository {
    @Query(value = "{'_id': :#{#id} }", fields = "{'comments':  0}")
    Optional<Book> findById(@Param("id") String id);

    @Query(value = "{'name': :#{#name} }", fields = "{'comments':  0}")
    List<Book> findByName(@Param("name") String name);

    @Query(value = "{}", fields = "{'comments':  0}")
    List<Book> findAll();

    @DeleteQuery(value = "{'_id': :#{#book.id} }")
    void delete(Book book);

    Book save(Book book);
}
