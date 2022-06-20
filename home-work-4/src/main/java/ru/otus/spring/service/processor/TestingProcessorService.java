package ru.otus.spring.service.processor;

import ru.otus.spring.domain.Testing;
import ru.otus.spring.service.io.IoService;

public interface TestingProcessorService {
    void processTesting(Testing testing);
}
