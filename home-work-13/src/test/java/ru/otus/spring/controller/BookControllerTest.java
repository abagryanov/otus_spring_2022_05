package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.spring.rest.dto.BookUpdateRequestDto;
import ru.otus.spring.rest.dto.BooksResponseDto;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    @SuppressWarnings("all")
    private MockMvc mvc;

    @Autowired
    @SuppressWarnings("all")
    private ObjectMapper objectMapper;

    @Test
    void whenDoGetAllBooksRequestNotAuth_thenResponseIsFound() throws Exception {
        mvc.perform(
                        get("/api/books"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "ivan1",
            roles = "USER"
    )
    @Test
    void whenDoGetAllBooksRequestAuthWithGrantedUser_thenResponseIsValid() throws Exception {
        mvc.perform(
                        get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @WithMockUser(
            username = "test",
            roles = "USER"
    )
    @Test
    void whenDoGetAllBooksRequestAuthWithNotGrantedUser_thenResponseIsEmpty() throws Exception {
        mvc.perform(
                        get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new BooksResponseDto(
                        Collections.emptyList()
                ))));
    }

    @Test
    void whenDoDeleteBookNotAuth_thenResponseIsFound() throws Exception {
        mvc.perform(
                        delete("/api/books/1"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "ivan1",
            roles = "USER"
    )
    @Test
    void whenDoDeleteBookAuth_thenResponseIsValid() throws Exception {
        mvc.perform(
                        delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "ivan1",
            roles = "USER"
    )
    @Test
    void whenDoEditBookAuth_thenResponseIsValid() throws Exception {
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
    void whenDoCreateBookNotAuth_thenResponseIsFound() throws Exception {
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
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "ivan1",
            roles = "USER"
    )
    @Test
    void whenDoCreateBookAuth_thenResponseIsValid() throws Exception {
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
