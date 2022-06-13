package ru.otus.spring.service.question;

import ru.otus.spring.domain.Question;

import java.util.Set;

public interface QuestionService {
    Set<Question> getQuestions();

    void printQuestions();
}
