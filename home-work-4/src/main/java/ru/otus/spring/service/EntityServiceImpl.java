package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EntityServiceImpl implements EntityService {
    private final AtomicInteger sequenceId = new AtomicInteger();

    @Override
    public int getId() {
        return sequenceId.incrementAndGet();
    }
}
