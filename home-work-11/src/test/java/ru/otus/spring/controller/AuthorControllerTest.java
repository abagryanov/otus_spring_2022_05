package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.spring.model.Author;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.rest.controller.AuthorController;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AuthorController.class)
public class AuthorControllerTest {
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenDoGetAuthors_thenResponseIsValid() {
        when(authorRepository.findAll()).thenReturn(Flux.just(
                new Author("1", "firstName1", "lastName1"),
                new Author("2", "firstName2", "lastName2")
        ));

        webTestClient.get()
                .uri("/api/authors")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
