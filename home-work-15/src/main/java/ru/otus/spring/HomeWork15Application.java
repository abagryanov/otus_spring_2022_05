package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.domain.Caterpillar;

@SpringBootApplication
public class HomeWork15Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HomeWork15Application.class, args);
        Incubator incubator = context.getBean(Incubator.class);
        incubator.process(new Caterpillar());
    }

}
