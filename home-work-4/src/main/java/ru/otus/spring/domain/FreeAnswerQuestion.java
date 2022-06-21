package ru.otus.spring.domain;

public class FreeAnswerQuestion extends Question {
    public FreeAnswerQuestion(int id, String text) {
        super(id, text);
    }

    @Override
    public String toString() {
        return "FreeAnswerQuestion{" +
                "id=" + getId() +
                ", text='" + getText() + '\'' +
                '}';
    }
}
