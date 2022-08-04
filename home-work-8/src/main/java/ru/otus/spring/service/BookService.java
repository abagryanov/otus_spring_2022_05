package ru.otus.spring.service;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BookService {
    Book findBookById(String id);

    void createBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    List<Book> getBooks();

    List<Author> getAuthors();

    List<Genre> getGenres();

    List<Comment> getBookComments(Book book);

    void createComment(Book book, Comment comment);

    void deleteComment(Book book, Comment comment);
}
