package ru.otus.spring.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.LimitedAnswerQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.io.IoServiceImpl;
import ru.otus.spring.service.l10n.LocalizationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component("LimitedAnswerQuestionConsoleProcessor")
@RequiredArgsConstructor
public class LimitedAnswerQuestionConsoleProcessor implements QuestionProcessor {
    private IoService ioService = new IoServiceImpl(System.in, System.out);
    private final LocalizationService localizationService;
    private final ApplicationProperties properties;

    @Override
    public void setIoService(IoService ioService) {
        this.ioService = ioService;
    }

    @Override
    public boolean processQuestion(Question question) {
        if (!(question instanceof LimitedAnswerQuestion)) {
            throw new IllegalArgumentException("Question is not instance of LimitedAnswerQuestion");
        }
        LimitedAnswerQuestion limitedAnswerQuestion = (LimitedAnswerQuestion) question;
        List<Answer> possibleAnswers = new ArrayList<>(limitedAnswerQuestion.getPossibleAnswers());
        int answerIndex = ioService.readIntWithPrompts(
                formatQuestionBody(limitedAnswerQuestion),
                formatAnswerHeader(possibleAnswers));
        return possibleAnswers.get(answerIndex).equals(limitedAnswerQuestion.getAnswer());
    }

    private String formatQuestionBody(LimitedAnswerQuestion limitedAnswerQuestion) {
        return String.format("%s #%d: %s",
                localizationService.getMessage(properties.getTestQuestionsPrefixKey()),
                limitedAnswerQuestion.getNumber(),
                localizationService.getMessage(limitedAnswerQuestion.getText()));
    }

    private String formatAnswerHeader(List<Answer> possibleAnswers) {
        return String.format("%s:", localizationService.getMessage(properties.getTestAnswersLimitedHeaderKey())) + '\n' +
                IntStream.range(0, possibleAnswers.size())
                        .mapToObj(i -> String.format("%d - %s", i,
                                localizationService.getMessage(possibleAnswers.get(i).getText())))
                        .collect(Collectors.joining("; "));
    }
}
