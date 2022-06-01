package ru.otus.spring.service.file;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> getLines(String filePath) {
        try (InputStream dataInputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            assert dataInputStream != null;
            return new BufferedReader(
                    new InputStreamReader(dataInputStream, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource file");
        }
    }
}
