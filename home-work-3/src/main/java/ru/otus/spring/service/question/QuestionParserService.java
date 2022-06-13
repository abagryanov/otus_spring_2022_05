package ru.otus.spring.service.question;

import ru.otus.spring.domain.Question;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface QuestionParserService {
    default Set<Question> parseLines(Collection<String> lines) {
        return lines.stream()
                .map(this::parseLine)
                .collect(Collectors.toSet());
    }
    Question parseLine(String line);
}
