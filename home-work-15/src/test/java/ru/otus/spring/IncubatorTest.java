package ru.otus.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.Caterpillar;

@SpringBootTest
public class IncubatorTest {
    @Autowired
    @SuppressWarnings("all")
    private Incubator incubator;

    @Test
    public void incubatorTest() {
        incubator.process(new Caterpillar());
    }
}
