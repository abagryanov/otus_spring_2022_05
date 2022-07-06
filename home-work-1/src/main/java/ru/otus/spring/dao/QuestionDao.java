package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.Set;

public interface QuestionDao {
    Set<Question> findAll();
}
