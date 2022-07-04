package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.file.FileReaderService;
import ru.otus.spring.service.question.QuestionParserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionDaoCsv implements QuestionDao {
    private final QuestionParserService questionParserService;

    private final FileReaderService fileReaderService;

    private final ApplicationProperties properties;

    @Override
    public Set<Question> findAll() {
        String csvFilePath = properties.getTestQuestionPath();
        return questionParserService.parseLines(fileReaderService.getLines(csvFilePath));
    }
}
