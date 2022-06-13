package ru.otus.spring.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Testing {
    private Map<Question, Boolean> testingResults = new HashMap<>();

    private final Set<Question> testingQuestions;

    public Testing(Set<Question> testingQuestions) {
        this.testingQuestions = testingQuestions;
    }

    public void addResult(Question question, boolean isRight) {
        testingResults.put(question, isRight);
    }

    public Map<Question, Boolean> getTestingResults() {
        return testingResults;
    }

    void setTestingResults(Map<Question, Boolean> testingResults) {
        this.testingResults = testingResults;
    }

    public Set<Question> getTestingQuestions() {
        return testingQuestions;
    }
}
