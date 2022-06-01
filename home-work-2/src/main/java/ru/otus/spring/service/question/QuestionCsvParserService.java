package ru.otus.spring.service.question;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.EntityService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuestionCsvParserService implements QuestionParserService {
    private final EntityService entityService;

    public QuestionCsvParserService(EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public Question parseLine(String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException("Question string should not be empty");
        }
        String[] tokens = line.split(";");
        return (tokens.length == 1) ?
                new FreeAnswerQuestion(entityService.getId(), tokens[0]) :
                parseLimitedAnswerQuestion(tokens);
    }

    private LimitedAnswerQuestion parseLimitedAnswerQuestion(String[] tokens) {
        return new LimitedAnswerQuestion(entityService.getId(), tokens[0],
                new Answer(entityService.getId(), tokens[tokens.length - 1]),
                IntStream.range(1, tokens.length - 1)
                        .mapToObj(i -> new Answer(entityService.getId(), tokens[i])
                        ).collect(Collectors.toSet()));
    }
}
