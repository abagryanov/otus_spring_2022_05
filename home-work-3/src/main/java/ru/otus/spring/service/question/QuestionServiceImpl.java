package ru.otus.spring.service.question;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Set<Question> getQuestions() {
        return questionDao.findAll();
    }

    @Override
    public void printQuestions() {
        getQuestions().forEach(question -> {
            System.out.println("Question: " + question.getText());
            if (question instanceof FreeAnswerQuestion) {
                System.out.println("Answers: Free answer");
            } else if (question instanceof LimitedAnswerQuestion) {
                System.out.println("Answers: " + ((LimitedAnswerQuestion) question).getPossibleAnswers().stream()
                        .map(Answer::getText).collect(Collectors.joining(", ")));
            }
        });
    }
}
