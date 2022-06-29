package ru.otus.spring.service.processor;

import ru.otus.spring.service.io.IoService;

public interface EntityActionProcessor {
    void processCreate();
    void processUpdate();
    void processDelete();
    void processShowAll();
    void setIoService(IoService ioService);
}
