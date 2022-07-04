package ru.otus.spring.service.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Description;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.DescriptionRepository;
import ru.otus.spring.repository.GenreRepository;
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
    BookRepository bookRepository;

    @MockBean
    GenreRepository genreRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    DescriptionRepository descriptionRepository;

    private static final List<Author> AVAILABLE_AUTHORS = List.of(
            new Author(1, "First Name 1", "Last Name1"),
            new Author(2, "First Name 2", "Last Name 2"));

    private static final List<Genre> AVAILABLE_GENRES = List.of(
            new Genre(1, "Genre 1"),
            new Genre(2, "Genre 2"));

    private static final Description AVAILABLE_DESCRIPTION =
            new Description(1, "Description 1");

    private static final List<Description> AVAILABLE_DESCRIPTIONS = List.of(
            new Description(1, "Description 1"),
            new Description(2, "Description 2")
    );
    private static final List<Book> AVAILABLE_BOOKS = Collections.singletonList(
            new Book(1, "Test Book", AVAILABLE_AUTHORS, AVAILABLE_GENRES, AVAILABLE_DESCRIPTION));

    @BeforeEach
    void setUp() {
        when(ioService.readStringWithPrompts("Enter book name:")).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains(" - "))).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Select"), any())).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Enter new book name"))).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains("Book"))).thenReturn("1");
        when(ioService.readIntWithPrompts(any())).thenReturn(1);
        bookActionProcessor.setIoService(ioService);

        when(authorRepository.findAll()).thenReturn(AVAILABLE_AUTHORS);
        when(genreRepository.findAll()).thenReturn(AVAILABLE_GENRES);
        when(bookRepository.findAll()).thenReturn(AVAILABLE_BOOKS);
        when(descriptionRepository.findAll()).thenReturn(AVAILABLE_DESCRIPTIONS);
    }

    @Test
    void whenDoProcessCreate_thenNewBookIsSaved() {
        bookActionProcessor.processCreate();
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void whenDoProcessUpdate_thenBookIsUpdated() {
        bookActionProcessor.processUpdate();
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void whenDoProcessDelete_thenBookIsDeleted() {
        bookActionProcessor.processDelete();
        verify(bookRepository, times(1)).delete(any());
    }
}
