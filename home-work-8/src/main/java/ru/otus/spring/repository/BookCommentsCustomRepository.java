package ru.otus.spring.repository;

import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import java.util.List;

public interface BookCommentsCustomRepository {
    void addComment(Book book, Comment comment);

    void deleteComment(Book book, Comment comment);

    List<Comment> getComments(Book book);
}
