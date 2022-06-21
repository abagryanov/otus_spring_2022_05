package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Question {
    @Getter
    @EqualsAndHashCode.Include
    private final int id;

    @Getter
    @Setter
    private int number;

    @Getter
    private final String text;

    public Question(int id, String text) {
        this.id = id;
        this.text = text;
    }
}
