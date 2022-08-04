package ru.otus.spring.service.processor;

public interface BookActionProcessor extends EntityActionProcessor {
    void processGetComments(String bookId);

    void processAddComment(String bookId);

    void processDeleteComments(String bookId);
}
