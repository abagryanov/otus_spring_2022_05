package ru.otus.spring.domain;

import java.util.Set;

public class LimitedAnswerQuestion extends Question {
    private final Answer answer;
    private final Set<Answer> possibleAnswers;
    public LimitedAnswerQuestion(int id, String text, Answer answer, Set<Answer> possibleAnswers) {
        super(id, text);
        this.answer = answer;
        this.possibleAnswers = possibleAnswers;
    }

    public Set<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public Answer getAnswer() {
        return answer;
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
