package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.rest.controller.GenreController;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = GenreController.class)
public class GenreControllerTest {
    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenDoGetGenres_thenResponseIsValid() {
        when(genreRepository.findAll()).thenReturn(Flux.just(
                new Genre("1", "genre1"),
                new Genre("2", "genre2")
        ));

        webTestClient.get()
                .uri("/api/genres")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
