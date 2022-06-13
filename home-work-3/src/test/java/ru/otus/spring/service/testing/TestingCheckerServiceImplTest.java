package ru.otus.spring.service.testing;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Testing;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestingCheckerServiceImplTest {

    private final TestingCheckerServiceImpl testingCheckerService = new TestingCheckerServiceImpl(0.5);

    @Test
    void whenDoCheckTestingWithNormalCountOfRightAnswers_thenReturnTrue() {
        Testing testing = new Testing(Collections.emptySet());
        testing.addResult(new LimitedAnswerQuestion(1, "test question1", new Answer(2, "test1"), Set.of()), true);
        testing.addResult(new LimitedAnswerQuestion(3, "test question2", new Answer(4, "test2"), Set.of()), true);
        assertTrue(testingCheckerService.checkTesting(testing));
    }

    @Test
    void whenDoCheckTestingWithoutNormalCountOfRightAnswers_thenReturnFalse() {
        Testing testing = new Testing(Collections.emptySet());
        testing.addResult(new LimitedAnswerQuestion(1, "test question1", new Answer(2, "test1"), Set.of()), true);
        testing.addResult(new LimitedAnswerQuestion(3, "test question2", new Answer(4, "test2"), Set.of()), false);
        testing.addResult(new LimitedAnswerQuestion(5, "test question2", new Answer(6, "test2"), Set.of()), false);
        assertFalse(testingCheckerService.checkTesting(testing));
    }
}
