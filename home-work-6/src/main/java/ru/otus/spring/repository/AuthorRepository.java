package ru.otus.spring.repository;

import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    Optional<Author> findById(long id);
    void delete(Author author);
    Author save(Author author);
}
