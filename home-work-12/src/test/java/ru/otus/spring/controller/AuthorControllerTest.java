package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.rest.controller.AuthorController;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    List<AuthorDto> EXPECTED_AUTHORS = List.of(
            new AuthorDto(1, "John1", "Doe1"),
            new AuthorDto(2, "John2", "Doe2")
    );

    @Autowired
    @SuppressWarnings("all")
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void whenDoGetAuthorsNotAuth_thenResponseIsUnauthorized() throws Exception {
        when(bookService.getAuthorsDto()).thenReturn(EXPECTED_AUTHORS);
        mvc.perform(
                        get("/api/authors")
                                .contentType("application/json")
                                .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "test",
            roles = "USER"
    )
    @Test
    void whenDoGetAuthorsAuth_thenResponseIsValid() throws Exception {
        when(bookService.getAuthorsDto()).thenReturn(EXPECTED_AUTHORS);
        mvc.perform(
                        get("/api/authors")
                                .contentType("application/json")
                                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
