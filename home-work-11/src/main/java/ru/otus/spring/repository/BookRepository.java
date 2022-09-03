package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

@Component
public interface BookRepository extends Repository<Book, String>, BookCustomRepository {
    @Query(value = "{'name': :#{#name} }", fields = "{'comments':  0}")
    Flux<Book> findByNameWithoutComments(@Param("name") String name);

    @DeleteQuery(value = "{'_id': :#{#id} }")
    Mono<Void> deleteById(@Param("id") String id);

    Mono<Book> save(Book book);
}
