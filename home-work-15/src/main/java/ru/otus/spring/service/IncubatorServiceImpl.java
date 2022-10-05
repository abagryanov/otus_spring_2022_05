package ru.otus.spring.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

@Service("incubatorService")
public class IncubatorServiceImpl implements IncubatorService {
    private final Logger log = LogManager.getLogger(IncubatorServiceImpl.class);

    @Override
    public Caterpillar processFattening(Caterpillar caterpillar) {
        log.info(String.format("Caterpillar %s has started fattening", caterpillar.getId()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to process fattening");
        }
        log.info(String.format("Caterpillar %s has finished fattening", caterpillar.getId()));
        return caterpillar;
    }

    @Override
    @SuppressWarnings("all")
    public Caterpillar processPupation(Caterpillar caterpillar) {
        log.info(String.format("Caterpillar %s has started pupation", caterpillar.getId()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to process fattening");
        }
        log.info(String.format("Caterpillar %s has finished pupation", caterpillar.getId()));
        return caterpillar;
    }

    @Override
    public Caterpillar processTransformation(Caterpillar caterpillar) {
        log.info(String.format("Caterpillar %s has started transformation", caterpillar.getId()));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to process fattening");
        }
        log.info(String.format("Caterpillar %s has finished transformation", caterpillar.getId()));
        return caterpillar;
    }

    public void processButterflyCreation(Butterfly butterfly) {
        log.info(String.format("New butterfly %s!", butterfly.getId()));
    }
}
