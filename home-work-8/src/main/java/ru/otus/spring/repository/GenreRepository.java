package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Genre;

@Component
public interface GenreRepository extends MongoRepository<Genre, String> {
}
