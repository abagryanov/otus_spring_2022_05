package ru.otus.spring.domain;

import lombok.Getter;

import java.util.Set;

public class LimitedAnswerQuestion extends Question {
    @Getter
    private final Answer answer;
    @Getter
    private final Set<Answer> possibleAnswers;
    public LimitedAnswerQuestion(int id, String text, Answer answer, Set<Answer> possibleAnswers) {
        super(id, text);
        this.answer = answer;
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public String toString() {
        return "LimitedAnswerQuestion{" +
                "answer=" + answer +
                ", possibleAnswers=" + possibleAnswers +
                ", id=" + getId() +
                ", text='" + getText() + '\'' +
                '}';
    }
}
