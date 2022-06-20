package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Answer {
    @Getter
    @EqualsAndHashCode.Include
    private final int id;

    @Getter
    private final String text;

    public Answer(int id, String text) {
        this.id = id;
        this.text = text;
    }
}
