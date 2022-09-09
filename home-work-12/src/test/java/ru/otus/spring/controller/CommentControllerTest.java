package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.rest.controller.CommentController;
import ru.otus.spring.rest.dto.AddCommentRequestDto;
import ru.otus.spring.security.SecurityConfiguration;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@Import(SecurityConfiguration.class)
public class CommentControllerTest {

    private static final BookDto EXPECTED_BOOK = new BookDto(1,
            "Book1",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>());

    @Autowired
    @SuppressWarnings("all")
    private MockMvc mvc;

    @Autowired
    @SuppressWarnings("all")
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void whenDoGetCommentsNotAuth_thenResponseIsFound() throws Exception {
        when(bookService.findBookById(anyLong())).thenReturn(EXPECTED_BOOK);
        mvc.perform(
                        get("/api/comments/1"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "test",
            roles = "USER"
    )
    @Test
    void whenDoGetCommentsAuth_thenResponseIsValid() throws Exception {
        when(bookService.findBookById(anyLong())).thenReturn(EXPECTED_BOOK);
        mvc.perform(
                        get("/api/comments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoDeleteCommentNotAuth_thenResponseIsFound() throws Exception {
        mvc.perform(
                delete("/api/comments/1"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "Test",
            roles = "USER"
    )
    @Test
    void whenDoDeleteCommentAuth_thenResponseIsValid() throws Exception {
        mvc.perform(
                        delete("/api/comments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoAddCommentNotAuth_thenResponseIsFound() throws Exception {
        AddCommentRequestDto addCommentRequestDto = new AddCommentRequestDto(1, "comment");
        when(bookService.findBookById(anyLong())).thenReturn(EXPECTED_BOOK);
        mvc.perform(
                post("/api/comments/create")
                        .contentType("application/json")
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(addCommentRequestDto)))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "Test",
            roles = "USER"
    )
    @Test
    void whenDoAddCommentAuth_thenResponseIsValid() throws Exception {
        AddCommentRequestDto addCommentRequestDto = new AddCommentRequestDto(1, "comment");
        when(bookService.findBookById(anyLong())).thenReturn(EXPECTED_BOOK);
        mvc.perform(
                        post("/api/comments/create")
                                .contentType("application/json")
                                .characterEncoding("utf-8")
                                .content(objectMapper.writeValueAsString(addCommentRequestDto)))
                .andExpect(status().isOk());
    }
}
