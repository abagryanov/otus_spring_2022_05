package ru.otus.spring.service.processor;

public interface EntityActionProcessor {
    void processCreate();
    void processUpdate(String id);
    void processDelete(String id);
    void processShowAll();
}
