package ru.otus.spring.repository;

import ru.otus.spring.domain.Question;
import ru.otus.spring.util.DialogUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDaoCsv implements QuestionDao {
    private final String csvPath;

    public QuestionDaoCsv(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public Set<Question> findAll() {
        try (InputStream dataInputStream = getClass().getClassLoader().getResourceAsStream(csvPath)) {
            assert dataInputStream != null;
            return new BufferedReader(
                    new InputStreamReader(dataInputStream, StandardCharsets.UTF_8)).lines()
                    .map(DialogUtil::parseCsvLine)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource file");
        }
    }
}
