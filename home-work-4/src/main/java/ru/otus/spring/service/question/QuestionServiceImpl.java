package ru.otus.spring.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    @Override
    public Set<Question> getQuestions() {
        return questionDao.findAll();
    }
}
