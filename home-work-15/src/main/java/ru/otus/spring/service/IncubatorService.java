package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

@Service
@SuppressWarnings("all")
public interface IncubatorService {
    Caterpillar processFattening(Caterpillar caterpillar);

    Caterpillar processPupation(Caterpillar caterpillar);

    Caterpillar processTransformation(Caterpillar caterpillar);

    void processButterflyCreation(Butterfly butterfly);
}
