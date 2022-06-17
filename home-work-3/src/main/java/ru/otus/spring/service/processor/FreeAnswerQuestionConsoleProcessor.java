package ru.otus.spring.service.processor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.FreeAnswerQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.io.IoServiceImpl;
import ru.otus.spring.service.l10n.LocalizationService;

@Component("FreeAnswerQuestionConsoleProcessor")
public class FreeAnswerQuestionConsoleProcessor implements QuestionProcessor {
    private IoService ioService = new IoServiceImpl(System.in, System.out);
    private final LocalizationService localizationService;
    private final String questionPrefixKey;
    private final String freeAnswerHeaderKey;

    public FreeAnswerQuestionConsoleProcessor(LocalizationService localizationService,
                                              @Value("${student.test.questions.prefix.key}")
                                              String questionPrefixKey,
                                              @Value("${student.test.answers.free.header.key}")
                                              String freeAnswerHeaderKey
                                              ) {
        this.localizationService = localizationService;
        this.questionPrefixKey = questionPrefixKey;
        this.freeAnswerHeaderKey = freeAnswerHeaderKey;
    }

    @Override
    public boolean processQuestion(Question question) {
        if (!(question instanceof FreeAnswerQuestion)) {
            throw new IllegalArgumentException("Question is not instance of FreeAnswerQuestion");
        }
        FreeAnswerQuestion freeAnswerQuestion = (FreeAnswerQuestion) question;
        String answerText = ioService.readStringWithPrompts(
                formatQuestionBody(freeAnswerQuestion),
                formatAnswerHeader());
        //Logic for checking
        return true;
    }

    @Override
    public void setIoService(IoService ioService) {
        this.ioService = ioService;
    }

    private String formatQuestionBody(FreeAnswerQuestion freeAnswerQuestion) {
        return String.format("%s #%d: %s",
                localizationService.getMessage(questionPrefixKey),
                freeAnswerQuestion.getNumber(),
                localizationService.getMessage(freeAnswerQuestion.getText()));
    }

    private String formatAnswerHeader() {
        return String.format("%s:",
                localizationService.getMessage(freeAnswerHeaderKey));
    }
}
