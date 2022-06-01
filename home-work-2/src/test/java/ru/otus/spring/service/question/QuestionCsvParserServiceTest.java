package ru.otus.spring.service.question;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.EntityService;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionCsvParserServiceTest {
    private static int idSequence = 0;

    private AutoCloseable openMocks;

    @Mock
    private EntityService entityService;

    @InjectMocks
    private QuestionCsvParserService questionParserService;

    @BeforeAll
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        when(entityService.getId()).thenReturn(idSequence++);
    }

    @Test
    void whenDoParseCsvLineWithOneToken_thenReturnFreeAnswerQuestion() {
        Question question = questionParserService.parseLine("Are you ok?;");
        assertTrue(question instanceof FreeAnswerQuestion);
        assertEquals(question.getText(), "Are you ok?");
    }

    @Test
    void whenDoParseCsvLineWithMultiTokens_thenReturnLimitedAnswerQuestion() {
        Question question = questionParserService.parseLine("Are you ok?;Yes;No;Yes");
        assertTrue(question instanceof LimitedAnswerQuestion);
        assertEquals("Are you ok?", question.getText());
        assertEquals(((LimitedAnswerQuestion) question).getPossibleAnswers().size(), 2);
    }

    @AfterAll
    void tearDown() throws Exception {
        openMocks.close();
    }
}
