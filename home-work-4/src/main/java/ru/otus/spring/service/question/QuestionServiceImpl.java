package ru.otus.spring.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    @Override
    public Set<Question> getQuestions() {
        return questionDao.findAll();
    }
}
