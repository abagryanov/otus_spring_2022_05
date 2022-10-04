package ru.otus.spring;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

@MessagingGateway
public interface Incubator {
    @Gateway(requestChannel = "caterpillarsChannel", replyChannel = "butterfliesChannel")
    Butterfly process(Caterpillar caterpillar);
}
