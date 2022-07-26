package ru.otus.spring.service.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.model.Book;
import ru.otus.spring.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BookRepositoryTest {
    private static final int EXPECTED_BOOKS_COUNT = 2;

    private static final int EXPECTED_QUERIES_COUNT = 3;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    @SuppressWarnings("all")
    private TestEntityManager testEntityManager;

    @Test
    void shouldSaveBook() {
        Book expectedBook = new Book("Bible");
        expectedBook = bookRepository.save(expectedBook);
        testEntityManager.flush();
        Optional<Book> actualBookOptional = bookRepository.findById(expectedBook.getId());
        assertThat(actualBookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @SuppressWarnings("all")
    void shouldReturnExpectedAllBooksWithExpextedQueriesCount() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        List<Book> currentBooks = bookRepository.findAll();
        assertThat(currentBooks.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
        currentBooks.forEach(book -> {
            assertThat(book.getAuthors().size()).isNotNull();
            assertThat(book.getGenres().size()).isNotNull();
        });
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount())
                .isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}
