package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.rest.dto.GenresResponseDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final BookService bookService;

    @GetMapping("/api/genres")
    public GenresResponseDto getGenres() {
        List<GenreDto> genres = bookService.getGenresDto();
        return new GenresResponseDto(genres);
    }
}
