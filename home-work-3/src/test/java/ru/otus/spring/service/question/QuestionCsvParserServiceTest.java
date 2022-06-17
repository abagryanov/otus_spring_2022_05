package ru.otus.spring.service.question;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.EntityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class QuestionCsvParserServiceTest {
    private static int idSequence = 0;

    @Mock
    private EntityService entityService;

    @InjectMocks
    private QuestionCsvParserService questionParserService;

    @BeforeEach
    void setUp() {
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
}
