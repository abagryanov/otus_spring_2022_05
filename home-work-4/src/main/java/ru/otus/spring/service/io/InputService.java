package ru.otus.spring.service.io;

public interface InputService {
    int readIntWithPrompts(String... prompts);
    String readStringWithPrompts(String... prompts);
}
