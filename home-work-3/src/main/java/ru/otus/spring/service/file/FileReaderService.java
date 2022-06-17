package ru.otus.spring.service.file;

import java.util.List;

public interface FileReaderService {
    List<String> getLines(String filePath);
}
