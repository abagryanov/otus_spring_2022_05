package ru.otus.spring.service.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class QuestionServiceImplTest {
    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        when(questionDao.findAll()).thenReturn(Set.of(
                new FreeAnswerQuestion(1, "Are you ok?"),
                new LimitedAnswerQuestion(2, "Are you fine?", new Answer(3, "Yes"), Set.of(
                        new Answer(3, "Yes"),
                        new Answer(4, "No")
                ))));
    }

    @Test
    void whenDoGetQuestions_thenReturnNonEmptyCollection() {
        Set<Question> questions = questionService.getQuestions();
        assertEquals(questions.size(), 2);
        assertEquals(1, questions.stream()
                .filter(q -> q instanceof FreeAnswerQuestion)
                .collect(Collectors.toSet()).size());
    }
}
