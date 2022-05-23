package ru.otus.spring.util;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class DialogUtil {
    private static int sequenceId = 0;

    private DialogUtil() {}

    public static int getId() {
        return sequenceId++;
    }

    public static Question parseCsvLine(String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException("Question string should not be empty");
        }
        String[] tokens = line.split(";");
        return (tokens.length == 1) ?
                new FreeAnswerQuestion(getId(), tokens[0]) :
                parseLimitedAnswerQuestion(tokens);
    }

    private static LimitedAnswerQuestion parseLimitedAnswerQuestion(String[] tokens) {
        return new LimitedAnswerQuestion(getId(), tokens[0],
                IntStream.range(1, tokens.length)
                        .mapToObj(i -> new Answer(getId(), tokens[i])
                        ).collect(Collectors.toSet()));
    }
}
