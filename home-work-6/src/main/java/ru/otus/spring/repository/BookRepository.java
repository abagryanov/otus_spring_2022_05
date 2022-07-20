package ru.otus.spring.repository;

import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(long id);
    void delete(Book book);
    Book save(Book book);
}
