package ru.otus.spring.service.processor;

public interface EntityActionProcessor {
    void processCreate();
    void processUpdate(long id);
    void processDelete(long id);
    void processShowAll();
}
