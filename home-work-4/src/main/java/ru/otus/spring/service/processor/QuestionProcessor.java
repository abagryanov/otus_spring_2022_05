package ru.otus.spring.service.processor;

import ru.otus.spring.domain.Question;
import ru.otus.spring.service.io.IoService;

public interface QuestionProcessor {
    boolean processQuestion(Question question);

    void setIoService(IoService ioService);
}
