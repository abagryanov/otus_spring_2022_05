package ru.otus.spring.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.file.FileReaderService;
import ru.otus.spring.service.question.QuestionParserService;

import java.util.Set;

@Service
public class QuestionDaoCsv implements QuestionDao {
    private final QuestionParserService questionParserService;

    private final FileReaderService fileReaderService;

    private final String csvFilePath;

    public QuestionDaoCsv(QuestionParserService questionParserService,
                          FileReaderService fileReaderService,
                          @Value("${student.test.question.path}") String csvFilePath) {
        this.questionParserService = questionParserService;
        this.fileReaderService = fileReaderService;
        this.csvFilePath = csvFilePath;
    }

    @Override
    public Set<Question> findAll() {
        return questionParserService.parseLines(fileReaderService.getLines(csvFilePath));
    }
}
