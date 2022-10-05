package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageHandler;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;
import ru.otus.spring.service.IncubatorService;

@Configuration
@EnableIntegration
public class IntegrationConfig {
    @Autowired
    @SuppressWarnings("all")
    private IncubatorService incubatorService;

    @Bean
    public QueueChannel caterpillarsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel butterfliesChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public GenericTransformer<Caterpillar, Butterfly> transformer() {
        return source -> new Butterfly(source.getId());
    }

    @Bean
    @ServiceActivator(inputChannel = "butterfliesChannel")
    public MessageHandler butterflyHandler() {
        return message -> {
            Butterfly butterfly = (Butterfly) message.getPayload();
            incubatorService.processButterflyCreation(butterfly);
        };
    }

    @Bean
    public IntegrationFlow incubatorFlow() {
        return IntegrationFlows.from("caterpillarsChannel")
                .handle("incubatorService", "processFattening")
                .handle("incubatorService", "processPupation")
                .handle("incubatorService", "processTransformation")
                .transform(transformer())
                .channel("butterfliesChannel")
                .get();
    }
}
