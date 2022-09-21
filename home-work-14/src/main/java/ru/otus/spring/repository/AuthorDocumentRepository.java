package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.mongo.AuthorDocument;

@Component
public interface AuthorDocumentRepository extends MongoRepository<AuthorDocument, String> {
}
