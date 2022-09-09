package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.rest.controller.GenreController;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    List<GenreDto> EXPECTED_GENRES = List.of(
            new GenreDto(1, "Genre1"),
            new GenreDto(2, "Genre2")
    );

    @Autowired
    @SuppressWarnings("all")
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void whenDoGetGenresNotAuth_thenResponseIsUnauyhorized() throws Exception {
        when(bookService.getGenresDto()).thenReturn(EXPECTED_GENRES);
        mvc.perform(
                        get("/api/genres")
                                .contentType("application/json")
                                .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "test",
            roles = "USER"
    )
    @Test
    void whenDoGetGenresAuth_thenResponseIsValid() throws Exception {
        when(bookService.getGenresDto()).thenReturn(EXPECTED_GENRES);
        mvc.perform(
                        get("/api/genres")
                                .contentType("application/json")
                                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
