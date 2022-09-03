package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @EventListener
    public void initData(ContextRefreshedEvent event) {
        Long authorsCount = authorRepository.count().block();
        if (authorsCount == null || authorsCount == 0) {
            List<Author> authors = List.of(
                    new Author("Alexander", "Pushkin"),
                    new Author("Charles", "Bukowski")
            );
            authorRepository.saveAll(authors).subscribe();
        }

        Long genresCount = genreRepository.count().block();
        if (genresCount == null || genresCount == 0) {
            List<Genre> genres = List.of(
                    new Genre("Fiction"),
                    new Genre("Prose")
            );
            genreRepository.saveAll(genres).subscribe();
        }
    }
}
