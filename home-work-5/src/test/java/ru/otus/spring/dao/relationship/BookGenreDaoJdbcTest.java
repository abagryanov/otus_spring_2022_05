package ru.otus.spring.dao.relationship;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.rowmapper.BookGenreMapper;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;
import ru.otus.spring.model.relationship.BookGenre;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({BookGenreDaoJdbc.class, BookGenreMapper.class})
public class BookGenreDaoJdbcTest {
    private static final int EXPECTED_BOOK_GENRES_COUNT = 2;
    private static final long EXISTING_BOOK_GENRE_ID = 1;
    private static final long EXISTING_ANOTHER_BOOK_GENRE_ID = 2;
    private static final long EXISTING_BOOK_GENRE_BOOK_ID = 1;
    private static final List<Long> EXISTING_BOOK_GENRE_GENRE_IDS = List.of(1L, 2L);

    @Autowired
    private BookGenreDaoJdbc bookGenreDaoJdbc;

    @Test
    void shouldReturnBookGenreById() {
        BookGenre expectedBookAuthor = new BookGenre(
                EXISTING_BOOK_GENRE_ID,
                new Book(EXISTING_BOOK_GENRE_BOOK_ID),
                new Genre(EXISTING_BOOK_GENRE_GENRE_IDS.get(0)));
        BookGenre actualBookGenre = bookGenreDaoJdbc.getById(EXISTING_BOOK_GENRE_ID);
        assertThat(actualBookGenre).usingRecursiveComparison().isEqualTo(expectedBookAuthor);
    }

    @Test
    void shouldReturnAllBookAuthorsByBook() {
        List<BookGenre> actualBookGenres = bookGenreDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_GENRE_BOOK_ID));
        assertThat(actualBookGenres.size()).isEqualTo(EXPECTED_BOOK_GENRES_COUNT);
        List<Long> actualGenreIds = actualBookGenres.stream()
                .map(bookGenre -> bookGenre.getGenre().getId())
                .collect(Collectors.toList());
        assertThat(actualGenreIds).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_GENRE_GENRE_IDS);
    }

    @Test
    void shouldUpdateAllBookAuthorsByBook() {
        List<BookGenre> expectedBookGenres = Collections.singletonList(
                new BookGenre(
                        new Book(1),
                        new Genre(1)));
        Book book = new Book(1);
        book.setGenres(Collections.singletonList(
                new Genre(1)));
        List<BookGenre> actualBookGenres = bookGenreDaoJdbc.updateAllByHolder(book);
        assertThat(actualBookGenres).usingDefaultComparator().isEqualTo(expectedBookGenres);
    }

    @Test
    void shouldDeleteBookAuthor() {
        BookGenre bookGenre = new BookGenre(1, new Book(), new Genre());
        bookGenreDaoJdbc.delete(bookGenre);
        List<BookGenre> actualBookGenres = bookGenreDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_GENRE_BOOK_ID));
        assertThat(actualBookGenres.size()).isEqualTo(1);
    }

    @Test
    void shouldDeleteAllBookAuthorsByBook() {
        Book book = new Book(EXISTING_BOOK_GENRE_BOOK_ID);
        bookGenreDaoJdbc.deleteAllByHolder(book);
        List<BookGenre> actualBookGenres = bookGenreDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_GENRE_BOOK_ID));
        assertThat(actualBookGenres.size()).isEqualTo(0);
    }

    @Test
    void shouldDeleteAllBookAuthorsByAuthor() {
        long existingGenreId = EXISTING_BOOK_GENRE_GENRE_IDS.get(0);
        Genre genre = new Genre(existingGenreId);
        bookGenreDaoJdbc.deleteAllByDependent(genre);
        List<BookGenre> actualBookGenres = bookGenreDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_GENRE_BOOK_ID));
        List<BookGenre> expectedBookGenres = actualBookGenres.stream()
                .filter(bookGenre -> bookGenre.getGenre().equals(genre))
                .collect(Collectors.toList());
        assertThat(expectedBookGenres.size()).isEqualTo(0);
    }

    @Test
    void shouldSaveBookAuthor() {
        BookGenre bookGenre = new BookGenre(new Book(2), new Genre(2));
        bookGenreDaoJdbc.save(bookGenre);
        List<BookGenre> actualBookGenres = bookGenreDaoJdbc.getAllByHolder(
                new Book(EXISTING_ANOTHER_BOOK_GENRE_ID));
        assertThat(actualBookGenres.size()).isEqualTo(2);
    }

    @Test
    void shouldSaveAllBookAuthorsByBook() {
        Book book = new Book(EXISTING_BOOK_GENRE_BOOK_ID);
        bookGenreDaoJdbc.deleteAllByHolder(book);
        List<BookGenre> expectedEmptyBookGenres = bookGenreDaoJdbc.getAllByHolder(book);
        assertThat(expectedEmptyBookGenres.size()).isEqualTo(0);
        book.setGenres(List.of(new Genre(1), new Genre(2)));
        bookGenreDaoJdbc.saveAllByHolder(book);
        List<BookGenre> expectedBookGenres = bookGenreDaoJdbc.getAllByHolder(book);
        assertThat(expectedBookGenres.size()).isEqualTo(2);
    }
}
