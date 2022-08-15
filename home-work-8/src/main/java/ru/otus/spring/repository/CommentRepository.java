package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Comment;

@Component
public interface CommentRepository extends MongoRepository<Comment, String> {
}
