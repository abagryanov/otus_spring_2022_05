package ru.otus.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    private BookService bookService;

    @Test
    void whenDoGetAllBooksRequest_thenResponseIsValid() throws Exception {
        when(bookService.getBooksDto()).thenReturn(EXPECTED_BOOKS);
        mvc.perform(
                        get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoDeleteBook_thenResponseIsRedirect() throws Exception {
        mvc.perform(
                        post("/delete/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenDoGetEditBook_thenResponseIsValid() throws Exception {
        when(bookService.findBookById(anyLong())).thenReturn(EXPECTED_BOOKS.get(0));
        mvc.perform(
                        get("/edit/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoEditBook_thenResponseIsRedirect() throws Exception {
        mvc.perform(
                        post("/edit"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenDoGetComments_thenResponseIsValid() throws Exception {
        mvc.perform(
                        get("/comments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoGetCreateBook_thenResponseIsValid() throws Exception {
        when(bookService.getAuthorsDto()).thenReturn(EXPECTED_AUTHORS);
        when(bookService.getGenresDto()).thenReturn(EXPECTED_GENRES);
        mvc.perform(
                        get("/create"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoCreateBook_thenResponseIsRedirect() throws Exception {
        mvc.perform(
                        post("/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenDoDeleteComment_thenResponseIsRedirect() throws Exception {
        mvc.perform(
                        post("/comments/delete/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void whenDoGetCreateComment_thenResponseIsValid() throws Exception {
        mvc.perform(
                        get("/comments/create/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoCreateComment_thenResponseIsRedirect() throws Exception {
        BookDto expectedBook = new BookDto(1, "Test book", Collections.emptyList(),
                Collections.emptyList(), new ArrayList<>());
        when(bookService.findBookById(anyLong())).thenReturn(expectedBook);
        mvc.perform(
                        post("/comments/create/1"))
                .andExpect(status().is3xxRedirection());
    }
}
