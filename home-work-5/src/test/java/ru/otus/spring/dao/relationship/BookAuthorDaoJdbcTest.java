package ru.otus.spring.dao.relationship;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.rowmapper.BookAuthorMapper;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.relationship.BookAuthor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({BookAuthorDaoJdbc.class, BookAuthorMapper.class})
public class BookAuthorDaoJdbcTest {
    private static final int EXPECTED_BOOK_AUTHORS_COUNT = 2;
    private static final long EXISTING_BOOK_AUTHOR_ID = 1;
    private static final long EXISTING_ANOTHER_BOOK_AUTHOR_ID = 2;
    private static final long EXISTING_BOOK_AUTHOR_BOOK_ID = 1;
    private static final List<Long> EXISTING_BOOK_AUTHOR_AUTHOR_IDS = List.of(1L, 2L);

    @Autowired
    private BookAuthorDaoJdbc bookAuthorDaoJdbc;

    @Test
    void shouldReturnBookAuthorById() {
        BookAuthor expectedBookAuthor = new BookAuthor(
                EXISTING_BOOK_AUTHOR_ID,
                new Book(EXISTING_BOOK_AUTHOR_BOOK_ID),
                new Author(EXISTING_BOOK_AUTHOR_AUTHOR_IDS.get(0)));
        BookAuthor actualBookAuthor = bookAuthorDaoJdbc.getById(EXISTING_BOOK_AUTHOR_ID);
        assertThat(actualBookAuthor).usingRecursiveComparison().isEqualTo(expectedBookAuthor);
    }

    @Test
    void shouldReturnAllBookAuthorsByBook() {
        List<BookAuthor> actualBookAuthors = bookAuthorDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_AUTHOR_BOOK_ID));
        assertThat(actualBookAuthors.size()).isEqualTo(EXPECTED_BOOK_AUTHORS_COUNT);
        List<Long> actualAuthorIds = actualBookAuthors.stream()
                        .map(bookAuthor -> bookAuthor.getAuthor().getId())
                                .collect(Collectors.toList());
        assertThat(actualAuthorIds).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_AUTHOR_AUTHOR_IDS);
    }

    @Test
    void shouldUpdateAllBookAuthorsByBook() {
        List<BookAuthor> expectedBookAuthors = Collections.singletonList(
                new BookAuthor(
                        new Book(1),
                        new Author(1)));
        Book book = new Book(1);
        book.setAuthors(Collections.singletonList(
                new Author(1)));
        List<BookAuthor> actualBookAuthors = bookAuthorDaoJdbc.updateAllByHolder(book);
        assertThat(actualBookAuthors).usingDefaultComparator().isEqualTo(expectedBookAuthors);
    }

    @Test
    void shouldDeleteBookAuthor() {
        BookAuthor bookAuthor = new BookAuthor(1, new Book(), new Author());
        bookAuthorDaoJdbc.delete(bookAuthor);
        List<BookAuthor> actualBookAuthors = bookAuthorDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_AUTHOR_BOOK_ID));
        assertThat(actualBookAuthors.size()).isEqualTo(1);
    }

    @Test
    void shouldDeleteAllBookAuthorsByBook() {
        Book book = new Book(EXISTING_BOOK_AUTHOR_BOOK_ID);
        bookAuthorDaoJdbc.deleteAllByHolder(book);
        List<BookAuthor> actualBookAuthors = bookAuthorDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_AUTHOR_BOOK_ID));
        assertThat(actualBookAuthors.size()).isEqualTo(0);
    }

    @Test
    void shouldDeleteAllBookAuthorsByAuthor() {
        long existingAuthorId = EXISTING_BOOK_AUTHOR_AUTHOR_IDS.get(0);
        Author author = new Author(existingAuthorId);
        bookAuthorDaoJdbc.deleteAllByDependent(author);
        List<BookAuthor> actualBookAuthors = bookAuthorDaoJdbc.getAllByHolder(
                new Book(EXISTING_BOOK_AUTHOR_BOOK_ID));
        List<BookAuthor> expectedBookAuthors = actualBookAuthors.stream()
                .filter(bookAuthor -> bookAuthor.getAuthor().equals(author))
                .collect(Collectors.toList());
        assertThat(expectedBookAuthors.size()).isEqualTo(0);
    }

    @Test
    void shouldSaveBookAuthor() {
        BookAuthor bookAuthor = new BookAuthor(new Book(2), new Author(2));
        bookAuthorDaoJdbc.save(bookAuthor);
        List<BookAuthor> actualBookAuthors = bookAuthorDaoJdbc.getAllByHolder(
                new Book(EXISTING_ANOTHER_BOOK_AUTHOR_ID));
        assertThat(actualBookAuthors.size()).isEqualTo(2);
    }

    @Test
    void shouldSaveAllBookAuthorsByBook() {
        Book book = new Book(EXISTING_BOOK_AUTHOR_BOOK_ID);
        bookAuthorDaoJdbc.deleteAllByHolder(book);
        List<BookAuthor> expectedEmptyBookAuthors = bookAuthorDaoJdbc.getAllByHolder(book);
        assertThat(expectedEmptyBookAuthors.size()).isEqualTo(0);
        book.setAuthors(List.of(new Author(1), new Author(2)));
        bookAuthorDaoJdbc.saveAllByHolder(book);
        List<BookAuthor> expectedBookAuthors = bookAuthorDaoJdbc.getAllByHolder(book);
        assertThat(expectedBookAuthors.size()).isEqualTo(2);
    }
}
