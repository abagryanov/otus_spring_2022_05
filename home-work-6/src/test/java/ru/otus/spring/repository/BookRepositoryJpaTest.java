package ru.otus.spring.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Description;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({BookRepositoryJpa.class, TestEntityManager.class})
public class BookRepositoryJpaTest {
    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_NAME = "White book";
    private static final String EXISTING_BOOK_DESCRIPTION = "Book about world and war";
    private static final int EXPECTED_QUERIES_COUNT = 3;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    @SuppressWarnings("all")
    private TestEntityManager testEntityManager;

    @Test
    void shouldReturnBookById() {
        Optional<Book> actualBookOptional = bookRepositoryJpa.findById(EXISTING_BOOK_ID);
        assertThat(actualBookOptional).isPresent();
        Book actualBook = actualBookOptional.get();
        assertThat(actualBook.getAuthors().size()).isEqualTo(2);
        assertThat(actualBook.getGenres().size()).isEqualTo(2);
        assertThat(actualBook.getDescription().getDescription()).isEqualTo(EXISTING_BOOK_DESCRIPTION);
        assertThat(actualBook.getName()).isEqualTo(EXISTING_BOOK_NAME);
    }

    @Test
    void shouldSaveBook() {
        Book expectedBook = new Book("Bible", new Description(1, "Book about world and war"));
        expectedBook = bookRepositoryJpa.save(expectedBook);
        Optional<Book> actualBookOptional = bookRepositoryJpa.findById(expectedBook.getId());
        assertThat(actualBookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        Optional<Book> expectedBookOptional = bookRepositoryJpa.findById(EXISTING_BOOK_ID);
        assertThat(expectedBookOptional).isPresent();
        Book expectedBook = expectedBookOptional.get();
        expectedBook.setName("New Bible");
        bookRepositoryJpa.save(expectedBook);
        Optional<Book> actualBookOptional = bookRepositoryJpa.findById(expectedBook.getId());
        assertThat(actualBookOptional).isPresent();
        Book actualBook = actualBookOptional.get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBook() {
        List<Book> currentBooks = bookRepositoryJpa.findAll();
        assertThat(currentBooks.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
        currentBooks.forEach(book -> bookRepositoryJpa.delete(book));
        assertThat(bookRepositoryJpa.findAll().size()).isEqualTo(0);
    }

    @Test
    @SuppressWarnings("all")
    void shouldReturnExpectedAllBooks() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        List<Book> currentBooks = bookRepositoryJpa.findAll();
        assertThat(currentBooks.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
        currentBooks.forEach(book -> {
            assertThat(book.getAuthors().size()).isNotNull();
            assertThat(book.getGenres().size()).isNotNull();
            assertThat(book.getDescription().getDescription()).isNotNull();
        });
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
                .isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}
