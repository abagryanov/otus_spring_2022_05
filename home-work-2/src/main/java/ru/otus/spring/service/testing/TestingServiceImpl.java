package ru.otus.spring.service.testing;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Testing;
import ru.otus.spring.service.question.QuestionService;

import java.util.*;

@Service
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;

    private final TestingCheckerService testingCheckerService;

    public TestingServiceImpl(QuestionService questionService,
                              TestingCheckerService testingCheckerService) {
        this.questionService = questionService;
        this.testingCheckerService = testingCheckerService;
    }

    @Override
    public void startTest() {
        Map<Answer, Answer> result = new HashMap<>();
        Scanner in = new Scanner(System.in);
        List<Question> questions = new ArrayList<>(questionService.getQuestions());
        for (int q = 0; q < questions.size(); q++) {
            Question question = questions.get(q);
            System.out.printf("Question #%d: %s%n", q + 1, question.getText());
            if (question instanceof LimitedAnswerQuestion) {
                List<Answer> possibleAnswers = new ArrayList<>(
                        ((LimitedAnswerQuestion) question).getPossibleAnswers());
                System.out.println("Choose answer:");
                for (int a = 0; a < possibleAnswers.size(); a++) {
                    System.out.printf("%d - %s; ", a, possibleAnswers.get(a).getText());
                }
                System.out.println();
                result.put(possibleAnswers.get(in.nextInt()),
                        ((LimitedAnswerQuestion) question).getAnswer());
            }
        }
        System.out.println( "Test is: " + (testingCheckerService.checkTesting(new Testing(result)) ? "success" : "failed"));
    }
}
