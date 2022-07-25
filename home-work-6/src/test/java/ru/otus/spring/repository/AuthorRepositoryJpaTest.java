package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({AuthorRepositoryJpa.class})
public class AuthorRepositoryJpaTest {
    private static final int EXPECTED_AUTHORS_COUNT = 2;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRST_NAME = "Charles";
    private static final String EXISTING_AUTHOR_LAST_NAME = "Bukowski";

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    void shouldReturnAuthorById() {
        Author expectedAuthor = new Author(
                EXISTING_AUTHOR_ID,
                EXISTING_AUTHOR_FIRST_NAME,
                EXISTING_AUTHOR_LAST_NAME);
        Optional<Author> actualAuthor = authorRepositoryJpa.findById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldSaveAuthor() {
        Author expectedAuthor = new Author("Mark", "Twain");
        expectedAuthor = authorRepositoryJpa.save(expectedAuthor);
        Optional<Author> actualAuthor = authorRepositoryJpa.findById(expectedAuthor.getId());
        assertThat(actualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author("Mark", "Twain");
        expectedAuthor = authorRepositoryJpa.save(expectedAuthor);
        expectedAuthor.setFirstName("Leo");
        expectedAuthor.setLastName("Tolstoy");
        authorRepositoryJpa.save(expectedAuthor);
        Optional<Author> actualAuthorOptional = authorRepositoryJpa.findById(expectedAuthor.getId());
        assertThat(actualAuthorOptional).isPresent();
        Author actualAuthor = actualAuthorOptional.get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldDeleteAuthor() {
        List<Author> currentAuthors = authorRepositoryJpa.findAll();
        assertThat(currentAuthors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
        currentAuthors.forEach(author -> authorRepositoryJpa.delete(author));
        assertThat(authorRepositoryJpa.findAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllAuthors() {
        List<Author> currentAuthors = authorRepositoryJpa.findAll();
        assertThat(currentAuthors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }
}
