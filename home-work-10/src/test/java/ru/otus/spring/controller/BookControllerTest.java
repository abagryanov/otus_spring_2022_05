package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.rest.controller.BookController;
import ru.otus.spring.rest.dto.BookUpdateRequestDto;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    List<AuthorDto> EXPECTED_AUTHORS = List.of(
            new AuthorDto(1, "John1", "Doe1"),
            new AuthorDto(2, "John2", "Doe2")
    );

    List<GenreDto> EXPECTED_GENRES = List.of(
            new GenreDto(1, "Genre1"),
            new GenreDto(2, "Genre2")
    );

    List<CommentDto> EXPECTED_COMMENTS = List.of(
            new CommentDto(1, "Comment1"),
            new CommentDto(2, "Comment 2")
    );

    List<BookDto> EXPECTED_BOOKS = List.of(
            new BookDto(1, "Test book1", EXPECTED_AUTHORS, EXPECTED_GENRES, EXPECTED_COMMENTS),
            new BookDto(2, "Test book2", EXPECTED_AUTHORS, EXPECTED_GENRES, EXPECTED_COMMENTS)
    );

    @Autowired
    @SuppressWarnings("all")
    private MockMvc mvc;

    @Autowired
    @SuppressWarnings("all")
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void whenDoGetAllBooksRequest_thenResponseIsValid() throws Exception {
        when(bookService.getBooksDto()).thenReturn(EXPECTED_BOOKS);
        mvc.perform(
                        get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoDeleteBook_thenResponseIsValid() throws Exception {
        mvc.perform(
                        delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoEditBook_thenResponseIsValid() throws Exception {
        BookUpdateRequestDto bookUpdateRequestDto = new BookUpdateRequestDto(
                1,
                "newName",
                new ArrayList<>(),
                new ArrayList<>());
        mvc.perform(
                        post("/api/books/edit")
                                .contentType("application/json")
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(bookUpdateRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoCreateBook_thenResponseIsRedirect() throws Exception {
        BookUpdateRequestDto bookUpdateRequestDto = new BookUpdateRequestDto(
                1,
                "newName",
                new ArrayList<>(),
                new ArrayList<>());
        mvc.perform(
                        post("/api/books/create")
                                .contentType("application/json")
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(bookUpdateRequestDto)))
                .andExpect(status().isOk());
    }
}
