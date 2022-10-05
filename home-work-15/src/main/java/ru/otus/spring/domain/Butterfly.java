package ru.otus.spring.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Butterfly {
    private final UUID id;
    private final LocalDateTime bornDateTime;

    public Butterfly(UUID id) {
        this.id = id;
        this.bornDateTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getBornDateTime() {
        return bornDateTime;
    }
}
