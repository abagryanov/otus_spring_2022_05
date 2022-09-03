package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping("/api/genres")
    public Flux<Genre> getGenres() {
        return genreRepository.findAll();
    }
}
