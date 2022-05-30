package ru.otus.spring.service.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Testing;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestingCheckerServiceImplTest {

    private final TestingCheckerServiceImpl testingCheckerService = new TestingCheckerServiceImpl(0.5);

    @Test
    void whenDoCheckTestingWithNormalCountOfRightAnswers_thenReturnTrue() {
        assertTrue(testingCheckerService.checkTesting(new Testing(Map.of(new Answer(1, "Yes"),
                new Answer(2, "Yes"), new Answer(3, "No"), new Answer(4, "No")))));
    }

    @Test
    void whenDoCheckTestingWithoutNormalCountOfRightAnswers_thenReturnFalse() {
        assertFalse(testingCheckerService.checkTesting(new Testing(Map.of(
                new Answer(1, "Yes"), new Answer(2, "No"),
                new Answer(3, "No"), new Answer(4, "No"),
                new Answer(5, "No"), new Answer(6, "Yes")))));
    }
}
