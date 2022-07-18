package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.io.IoServiceImpl;

@Configuration
public class BookActionConfiguration {
    @Bean
    public IoService ioService() {
        return new IoServiceImpl(System.in, System.out);
    }
}
