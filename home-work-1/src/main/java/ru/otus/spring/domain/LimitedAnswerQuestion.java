package ru.otus.spring.domain;

import java.util.Set;

public class LimitedAnswerQuestion extends DialogEntity implements Question {

    private final Set<Answer> possibleAnswers;

    public LimitedAnswerQuestion(Set<Answer> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public LimitedAnswerQuestion(int id, String text, Set<Answer> possibleAnswers) {
        super(id, text);
        this.possibleAnswers = possibleAnswers;
    }


    public Set<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public String toString() {
        return "LimitedAnswerQuestion{" +
                "possibleAnswers=" + possibleAnswers +
                ", id=" + getId() +
                ", text='" + getText() + '\'' +
                '}';
    }
}
