package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import ru.otus.spring.model.Comment;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.rest.controller.CommentController;
import ru.otus.spring.rest.dto.AddCommentRequestDto;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CommentController.class)
public class CommentControllerTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenDoGetComments_thenResponseIsValid() {
        when(bookRepository.getCommentsByBookId(anyString()))
                .thenReturn(Flux.just(
                        new Comment("1", "comment1"),
                        new Comment("2", "comment2")
                ));
        webTestClient.get()
                .uri("/api/books/1/comments")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void whenDoDeleteComment_thenResponseIsValid() {
        webTestClient.delete()
                .uri("/api/books/1/comments/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void whenDoAddComment_thenResponseIsValid() {
        AddCommentRequestDto comment = new AddCommentRequestDto(null, "comment");
        webTestClient.post()
                .uri("/api/books/comments")
                .bodyValue(comment)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
