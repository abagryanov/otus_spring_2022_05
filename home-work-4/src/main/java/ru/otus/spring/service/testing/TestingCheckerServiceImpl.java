package ru.otus.spring.service.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.domain.Testing;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TestingCheckerServiceImpl implements TestingCheckerService {
    private final ApplicationProperties properties;

    @Override
    public boolean checkTesting(Testing testing) {
        double successCoefficient = properties.getTestSuccessCoefficient();
        int size = testing.getTestingResults().size();
        return (testing.getTestingResults().entrySet().stream()
                .filter(Map.Entry::getValue)
                .count() / (double) size) >= successCoefficient;
    }
}
