package ru.otus.spring.service.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.l10n.LocalizationService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LimitedAnswerQuestionConsoleProcessorTest {
    @Mock
    IoService ioService;

    @Mock
    LocalizationService localizationService;

    @InjectMocks
    LimitedAnswerQuestionConsoleProcessor limitedAnswerQuestionConsoleProcessor;

    @BeforeEach
    void setUp() {
        when(ioService.readIntWithPrompts(anyString(), anyString())).thenReturn(0);
        when(ioService.readStringWithPrompts(anyString())).thenReturn("text");
        when(ioService.readStringWithPrompts(anyString(), anyString())).thenReturn("text");
        when(localizationService.getMessage(anyString())).thenReturn("message");
        limitedAnswerQuestionConsoleProcessor.setIoService(ioService);
    }

    @Test
    void whenDoProcessLimitedAnswerQuestion_thenReturnTrue() {
        assertTrue(limitedAnswerQuestionConsoleProcessor.processQuestion(new LimitedAnswerQuestion(
                1,
                "question1",
                new Answer(2,"answer1"),
                Set.of(new Answer(2,"answer1"))
        )));
    }
}
