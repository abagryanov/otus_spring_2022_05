package ru.otus.spring.repository;

import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import java.util.List;

public interface CommentRepository extends EntityRepository<Comment> {
    List<Comment> findAllByBook(Book book);
}
