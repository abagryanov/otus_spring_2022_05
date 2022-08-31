package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.rest.controller.CommentController;
import ru.otus.spring.rest.dto.AddCommentRequestDto;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
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
    void whenDoGetComments_thenResponseIsValid() throws Exception {
        when(bookService.findBookById(anyLong())).thenReturn(EXPECTED_BOOK);
        mvc.perform(
                        get("/api/comments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoDeleteComment_thenResponseIsValid() throws Exception {
        mvc.perform(
                delete("/api/comments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDoAddComment_thenResponseIsValid() throws Exception {
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
