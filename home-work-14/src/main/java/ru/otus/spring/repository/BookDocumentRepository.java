package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.mongo.BookDocument;

@Component
public interface BookDocumentRepository extends MongoRepository<BookDocument, String> {
}
