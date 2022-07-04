package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({GenreRepositoryJpa.class})
public class GenreRepositoryJpaTest {
    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    private static final int EXPECTED_GENRES_COUNT = 2;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Fiction";

    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = new Genre(
                EXISTING_GENRE_ID,
                EXISTING_GENRE_NAME);
        Optional<Genre> actualGenreOptional = genreRepositoryJpa.findById(EXISTING_GENRE_ID);
        assertThat(actualGenreOptional).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldSaveGenre() {
        Genre expectedGenre = new Genre( "Thriller");
        expectedGenre = genreRepositoryJpa.save(expectedGenre);
        Optional<Genre> actualGenreOptional = genreRepositoryJpa.findById(expectedGenre.getId());
        assertThat(actualGenreOptional).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre("Thriller");
        expectedGenre = genreRepositoryJpa.save(expectedGenre);
        expectedGenre.setName("Poetry");
        genreRepositoryJpa.save(expectedGenre);
        Optional<Genre> actualGenreOptional = genreRepositoryJpa.findById(expectedGenre.getId());
        assertThat(actualGenreOptional).isPresent().get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldDeleteGenre() {
        List<Genre> currentGenres = genreRepositoryJpa.findAll();
        assertThat(currentGenres.size()).isEqualTo(EXPECTED_GENRES_COUNT);
        currentGenres.forEach(author -> genreRepositoryJpa.delete(author));
        assertThat(genreRepositoryJpa.findAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllGenres() {
        List<Genre> currentGenres = genreRepositoryJpa.findAll();
        assertThat(currentGenres.size()).isEqualTo(EXPECTED_GENRES_COUNT);
    }
}
