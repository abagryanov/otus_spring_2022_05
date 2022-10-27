package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.rest.dto.AuthorsResponseDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final BookService bookService;

    @GetMapping("/api/authors")
    public AuthorsResponseDto getAuthors() {
        List<AuthorDto> authors = bookService.getAuthorsDto();
        return new AuthorsResponseDto(authors);
    }
}
