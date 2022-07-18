package ru.otus.spring.service.processor;

public interface BookActionProcessor extends EntityActionProcessor {
    void processGetComments(long bookId);

    void processAddComment(long bookId);

    void processDeleteComments(long bookId);
}
