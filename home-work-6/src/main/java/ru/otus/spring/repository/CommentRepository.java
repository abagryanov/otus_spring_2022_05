package ru.otus.spring.repository;

import ru.otus.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAll();
    Optional<Comment> findById(long id);
    void delete(Comment comment);
    Comment save(Comment comment);
}
