package ru.otus.spring.dao.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.relationship.BookAuthorDaoJdbc;
import ru.otus.spring.dao.rowmapper.AuthorMapper;
import ru.otus.spring.dao.rowmapper.BookAuthorMapper;
import ru.otus.spring.model.entity.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({AuthorDaoJdbc.class, BookAuthorDaoJdbc.class, AuthorMapper.class, BookAuthorMapper.class})
public class AuthorDaoJdbcTest {
    private static final int EXPECTED_AUTHORS_COUNT = 2;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_FIRST_NAME = "Charles";
    private static final String EXISTING_AUTHOR_LAST_NAME = "Bukowski";

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void shouldReturnAuthorById() {
        Author expectedAuthor = new Author(
                EXISTING_AUTHOR_ID,
                EXISTING_AUTHOR_FIRST_NAME,
                EXISTING_AUTHOR_LAST_NAME);
        Author actualAuthor = authorDaoJdbc.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldSaveAuthor() {
        Author expectedAuthor = new Author("Mark", "Twain");
        expectedAuthor = authorDaoJdbc.save(expectedAuthor);
        Author actualAuthor = authorDaoJdbc.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author("Mark", "Twain");
        expectedAuthor = authorDaoJdbc.save(expectedAuthor);
        expectedAuthor.setFirstName("Leo");
        expectedAuthor.setLastName("Tolstoy");
        authorDaoJdbc.update(expectedAuthor);
        Author actualAuthor = authorDaoJdbc.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldDeleteAuthor() {
        List<Author> currentAuthors = authorDaoJdbc.getAll();
        assertThat(currentAuthors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
        currentAuthors.forEach(author -> authorDaoJdbc.delete(author));
        assertThat(authorDaoJdbc.getAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllAuthors() {
        List<Author> currentAuthors = authorDaoJdbc.getAll();
        assertThat(currentAuthors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }
}
