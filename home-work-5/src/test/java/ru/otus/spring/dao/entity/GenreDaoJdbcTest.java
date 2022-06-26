package ru.otus.spring.dao.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.relationship.BookGenreDaoJdbc;
import ru.otus.spring.dao.rowmapper.BookGenreMapper;
import ru.otus.spring.dao.rowmapper.GenreMapper;
import ru.otus.spring.model.entity.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({GenreDaoJdbc.class, BookGenreDaoJdbc.class, GenreMapper.class, BookGenreMapper.class})
public class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    private static final int EXPECTED_GENRES_COUNT = 2;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Fiction";

    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = new Genre(
                EXISTING_GENRE_ID,
                EXISTING_GENRE_NAME);
        Genre actualGenre = genreDaoJdbc.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldSaveGenre() {
        Genre expectedGenre = new Genre( "Thriller");
        expectedGenre = genreDaoJdbc.save(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre("Thriller");
        expectedGenre = genreDaoJdbc.save(expectedGenre);
        expectedGenre.setName("Poetry");
        genreDaoJdbc.update(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldDeleteGenre() {
        List<Genre> currentGenres = genreDaoJdbc.getAll();
        assertThat(currentGenres.size()).isEqualTo(EXPECTED_GENRES_COUNT);
        currentGenres.forEach(author -> genreDaoJdbc.delete(author));
        assertThat(genreDaoJdbc.getAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllGenres() {
        List<Genre> currentGenres = genreDaoJdbc.getAll();
        assertThat(currentGenres.size()).isEqualTo(EXPECTED_GENRES_COUNT);
    }
}
