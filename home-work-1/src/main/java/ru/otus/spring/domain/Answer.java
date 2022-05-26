package ru.otus.spring.domain;

public class Answer extends DialogEntity {
    public Answer() {
        super();
    }

    public Answer(int id, String text) {
        super(id, text);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", text='" + getText() + '\'' +
                '}';
    }
}
