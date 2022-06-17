package ru.otus.spring.service.processor;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Testing;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.io.IoServiceImpl;
import ru.otus.spring.service.testing.TestingCheckerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConsoleTestingProcessorService implements TestingProcessorService {
    private final IoService ioService = new IoServiceImpl(System.in, System.out);

    private final Map<String, QuestionProcessor> questionProcessorMap;

    private final TestingCheckerService testingCheckerService;

    public ConsoleTestingProcessorService(Map<String, QuestionProcessor> questionProcessorMap,
                                          TestingCheckerService testingCheckerService) {
        this.questionProcessorMap = questionProcessorMap;
        this.testingCheckerService = testingCheckerService;
    }

    @Override
    public void processTesting(Testing testing) {
        List<Question> questions = new ArrayList<>(testing.getTestingQuestions());
        for (int q = 0; q < questions.size(); q++) {
            Question question = questions.get(q);
            question.setNumber(q + 1);
            testing.addResult(question, getQuestionProcessor(question).processQuestion(question));
        }
        ioService.printString("Test is: " + (testingCheckerService.checkTesting(testing) ? "success" : "failed"));
    }

    private QuestionProcessor getQuestionProcessor(Question question) {
        QuestionProcessor questionProcessor = questionProcessorMap
                .get(question.getClass().getSimpleName() + "ConsoleProcessor");
        if (questionProcessor == null) {
            throw new RuntimeException("No registered console processor for question: " + question.getClass());
        }
        return questionProcessor;
    }
}
