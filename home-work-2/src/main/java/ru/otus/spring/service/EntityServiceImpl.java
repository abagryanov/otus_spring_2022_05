package ru.otus.spring.service;

import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl implements EntityService {
    private static int sequenceId = 0;

    @Override
    public int getId() {
        return sequenceId++;
    }
}
