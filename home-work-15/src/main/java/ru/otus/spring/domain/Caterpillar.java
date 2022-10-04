package ru.otus.spring.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Caterpillar {
    private final UUID id;

    private final LocalDateTime bornDateTime;

    public Caterpillar() {
        this.id = UUID.randomUUID();
        this.bornDateTime = LocalDateTime.now();
    }

    public Caterpillar(UUID id, LocalDateTime bornDateTime) {
        this.id = id;
        this.bornDateTime = bornDateTime;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getBornDateTime() {
        return bornDateTime;
    }
}
