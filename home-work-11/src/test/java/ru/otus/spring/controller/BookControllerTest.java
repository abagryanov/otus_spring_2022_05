package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.rest.controller.BookController;
import ru.otus.spring.rest.dto.BookUpdateRequestDto;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookController.class)
public class BookControllerTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenDoGetAllBooksRequest_thenResponseIsValid() {
        when(bookRepository.findAllWithoutComments()).thenReturn(Flux.just(
                new BookDto("1", "name1", new ArrayList<>(), new ArrayList<>()),
                new BookDto("2", "name2", new ArrayList<>(), new ArrayList<>())
        ));
        webTestClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void whenDoDeleteBook_thenResponseIsValid() {
        webTestClient.delete().
                uri("/api/books/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void whenDoEditBook_thenResponseIsValid() {
        BookUpdateRequestDto bookUpdateRequestDto = new BookUpdateRequestDto(
                "1",
                "newName",
                new ArrayList<>(),
                new ArrayList<>());
        webTestClient.post()
                .uri("/api/books/")
                .bodyValue(bookUpdateRequestDto)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void whenDoCreateBook_thenResponseIsRedirect() {
        BookUpdateRequestDto bookUpdateRequestDto = new BookUpdateRequestDto(
                null,
                "newName",
                new ArrayList<>(),
                new ArrayList<>());
        webTestClient.post()
                .uri("/api/books/")
                .bodyValue(bookUpdateRequestDto)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
