package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.mongo.BookDocument;

import java.util.List;
import java.util.Optional;

@Component
public interface BookDocumentRepository extends Repository<BookDocument, String>, BookDocumentCustomRepository {
    @Query(value = "{'_id': :#{#id} }", fields = "{'comments':  0}")
    Optional<BookDocument> findByIdWithoutComments(@Param("id") String id);

    @Query(value = "{'name': :#{#name} }", fields = "{'comments':  0}")
    List<BookDocument> findByNameWithoutComments(@Param("name") String name);

    @Query(value = "{}", fields = "{'comments':  0}")
    List<BookDocument> findAllWithoutComments();

    @DeleteQuery(value = "{'_id': :#{#book.id} }")
    void delete(BookDocument book);

    BookDocument save(BookDocument book);
}
