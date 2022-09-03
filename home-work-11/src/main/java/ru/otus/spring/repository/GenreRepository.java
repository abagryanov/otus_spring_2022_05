package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Genre;

@Component
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
