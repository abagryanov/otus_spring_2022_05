package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Author;

@Component
public interface AuthorRepository extends MongoRepository<Author, String> {
}
