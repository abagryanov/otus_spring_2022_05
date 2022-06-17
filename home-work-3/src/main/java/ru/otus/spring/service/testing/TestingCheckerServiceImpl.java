package ru.otus.spring.service.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Testing;

import java.util.Map;

@Service
public class TestingCheckerServiceImpl implements TestingCheckerService {
    private final double successCoefficient;

    public TestingCheckerServiceImpl(@Value("${student.test.success.coefficient}") double successCoefficient) {
        this.successCoefficient = successCoefficient;
    }

    @Override
    public boolean checkTesting(Testing testing) {
        int size = testing.getTestingResults().size();
        return (testing.getTestingResults().entrySet().stream()
                .filter(Map.Entry::getValue)
                .count() / (double) size) >= successCoefficient;
    }
}
