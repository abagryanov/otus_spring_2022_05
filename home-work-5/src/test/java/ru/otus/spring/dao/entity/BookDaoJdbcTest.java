package ru.otus.spring.dao.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.extractor.BookAuthorResultSetExtractor;
import ru.otus.spring.dao.extractor.BookGenreResultSetExtractor;
import ru.otus.spring.dao.relationship.BookAuthorDaoJdbc;
import ru.otus.spring.dao.relationship.BookGenreDaoJdbc;
import ru.otus.spring.dao.rowmapper.BookAuthorMapper;
import ru.otus.spring.dao.rowmapper.BookGenreMapper;
import ru.otus.spring.model.entity.Book;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({BookDaoJdbc.class, BookAuthorDaoJdbc.class, BookGenreDaoJdbc.class,
        BookAuthorMapper.class, BookGenreMapper.class,
        BookAuthorResultSetExtractor.class, BookGenreResultSetExtractor.class})
public class BookDaoJdbcTest {
    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_NAME = "White book";

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void shouldReturnBookById() {
        Book actualBook = bookDaoJdbc.getById(EXISTING_BOOK_ID);
        assertThat(actualBook.getAuthors().size()).isEqualTo(2);
        assertThat(actualBook.getGenres().size()).isEqualTo(2);
        assertThat(actualBook.getName()).isEqualTo(EXISTING_BOOK_NAME);
    }

    @Test
    void shouldSaveBook() {
        Book expectedBook = new Book("Bible");
        expectedBook = bookDaoJdbc.save(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        Book expectedBook = bookDaoJdbc.getById(EXISTING_BOOK_ID);
        expectedBook.setName("New Bible");
        bookDaoJdbc.update(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBook() {
        List<Book> currentBooks = bookDaoJdbc.getAll();
        assertThat(currentBooks.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
        currentBooks.forEach(book -> bookDaoJdbc.delete(book));
        assertThat(bookDaoJdbc.getAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllBooks() {
        List<Book> currentBooks = bookDaoJdbc.getAll();
        assertThat(currentBooks.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }
}
