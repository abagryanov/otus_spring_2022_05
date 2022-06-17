package ru.otus.spring.service.testing;

import org.springframework.stereotype.Service;

import ru.otus.spring.domain.Testing;
import ru.otus.spring.service.processor.TestingProcessorService;
import ru.otus.spring.service.question.QuestionService;

@Service
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;

    private final TestingProcessorService testingProcessorService;

    public TestingServiceImpl(QuestionService questionService,
                              TestingProcessorService testingProcessorService) {
        this.questionService = questionService;
        this.testingProcessorService = testingProcessorService;
    }

    @Override
    public void startTest() {
        Testing testing = new Testing(questionService.getQuestions());
        testingProcessorService.processTesting(testing);
    }
}
