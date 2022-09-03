package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Author;

@Component
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
