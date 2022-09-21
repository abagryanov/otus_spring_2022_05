package ru.otus.spring.repository;

import ru.otus.spring.model.mongo.BookDocument;
import ru.otus.spring.model.mongo.CommentDocument;

import java.util.List;

public interface BookDocumentCustomRepository {
    void addComment(BookDocument book, CommentDocument comment);

    void deleteComment(BookDocument book, CommentDocument comment);

    List<CommentDocument> getComments(BookDocument book);
}
