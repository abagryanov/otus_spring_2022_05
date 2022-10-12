package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Comment;

@Component
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
