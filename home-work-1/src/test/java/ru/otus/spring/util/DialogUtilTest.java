package ru.otus.spring.util;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;

import static org.junit.jupiter.api.Assertions.*;

public class DialogUtilTest {

    @Test
    void whenDoParseCsvLineWithOneToken_thenReturnFreeAnswerQuestion() {
        Question question = DialogUtil.parseCsvLine("Are you ok?;");
        assertTrue(question instanceof FreeAnswerQuestion);
        assertEquals("Are you ok?", question.getText());
    }

    @Test
    void whenDoParseCsvLineWithMultiTokens_thenReturnLimitedAnswerQuestion() {
        Question question = DialogUtil.parseCsvLine("Are you ok?;Yes;No");
        assertTrue(question instanceof LimitedAnswerQuestion);
        assertEquals("Are you ok?", question.getText());
        assertEquals(((LimitedAnswerQuestion) question).getPossibleAnswers().size(), 2);
    }
}
