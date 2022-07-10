package ru.otus.spring.service;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BookService {
    void createBook(Book book);
    void updateBook(Book book);
    void deleteBook(Book book);
    List<Book> getBooks();
    List<Author> getAuthors();
    List<Genre> getGenres();
}
