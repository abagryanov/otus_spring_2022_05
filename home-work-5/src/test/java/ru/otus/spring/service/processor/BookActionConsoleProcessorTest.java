package ru.otus.spring.service.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.entity.AuthorDao;
import ru.otus.spring.dao.entity.BookDao;
import ru.otus.spring.dao.entity.GenreDao;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.validator.UserInputValidator;
import ru.otus.spring.validator.UserInputValidatorImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookActionConsoleProcessor.class, UserInputValidatorImpl.class})
public class BookActionConsoleProcessorTest {
    @Autowired
    BookActionProcessor bookActionProcessor;

    @Autowired
    UserInputValidator userInputValidator;

    @MockBean
    IoService ioService;

    @MockBean
    BookDao bookDao;

    @MockBean
    GenreDao genreDao;

    @MockBean
    AuthorDao authorDao;

    private static List<Author> AVAILABLE_AUTHORS = List.of(
            new Author(1, "First Name 1", "Last Name1"),
            new Author(2, "First Name 2", "Last Name 2"));

    private static List<Genre> AVAILABLE_GENRES = List.of(
            new Genre(1, "Genre 1"),
            new Genre(2, "Genre 2"));

    private static List<Book> AVAILABLE_BOOKS = Collections.singletonList(
            new Book(1, "Test Book", AVAILABLE_AUTHORS, AVAILABLE_GENRES));

    @BeforeEach
    void setUp() {
        when(ioService.readStringWithPrompts("Enter book name:")).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains(" - "))).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Select"), any())).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Enter new book name"))).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains("Book"))).thenReturn("1");
        when(ioService.readIntWithPrompts(any())).thenReturn(1);
        bookActionProcessor.setIoService(ioService);

        when(authorDao.getAll()).thenReturn(AVAILABLE_AUTHORS);
        when(genreDao.getAll()).thenReturn(AVAILABLE_GENRES);
        when(bookDao.getAll()).thenReturn(AVAILABLE_BOOKS);
    }

    @Test
    void whenDoProcessCreate_thenNewBookIsSaved() {
        bookActionProcessor.processCreate();
        verify(bookDao, times(1)).save(any());
    }

    @Test
    void whenDoProcessUpdate_thenBookIsUpdated() {
        bookActionProcessor.processUpdate();
        verify(bookDao, times(1)).update(any());
    }

    @Test
    void whenDoProcessDelete_thenBookIsDeleted() {
        bookActionProcessor.processDelete();
        verify(bookDao, times(1)).delete(any());
    }
}
