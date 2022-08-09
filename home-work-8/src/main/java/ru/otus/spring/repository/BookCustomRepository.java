package ru.otus.spring.repository;

import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import java.util.List;

public interface BookCustomRepository {
    void addComment(Book book, Comment comment);

    void deleteComment(Book book, Comment comment);

    List<Comment> getComments(Book book);
}
