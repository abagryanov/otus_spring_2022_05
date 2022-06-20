package ru.otus.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student")
@Getter
@Setter
public class ApplicationProperties {
    private String testLocale;
    private String testQuestionPath;
    private double testSuccessCoefficient;
    private String testQuestionsPrefixKey;
    private String testAnswersLimitedHeaderKey;
    private String testAnswersFreeHeaderKey;
}
