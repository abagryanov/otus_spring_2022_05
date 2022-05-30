package ru.otus.spring.domain;

import java.util.HashMap;
import java.util.Map;

public class Testing {
    private Map<Answer, Answer> testingResults = new HashMap<>();

    public Testing() {}

    public Testing(Map<Answer, Answer> testingResults) {
        this.testingResults = testingResults;
    }

    public void addResult(Answer userAnswer, Answer rightAnswer) {
        testingResults.put(userAnswer, rightAnswer);
    }

    public Map<Answer, Answer> getTestingResults() {
        return testingResults;
    }
}
