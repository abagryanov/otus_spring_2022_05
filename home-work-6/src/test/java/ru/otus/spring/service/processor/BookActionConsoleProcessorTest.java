package ru.otus.spring.service.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.validator.UserInputValidatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = {BookActionConsoleProcessor.class, UserInputValidatorImpl.class})
@DataJpaTest
@Import({BookActionConsoleProcessor.class, UserInputValidatorImpl.class})
public class BookActionConsoleProcessorTest {
    @Autowired
    private BookActionProcessor bookActionProcessor;

    @MockBean
    private IoService ioService;

    @MockBean
    private BookService bookService;

    private static final List<Author> AVAILABLE_AUTHORS = List.of(
            new Author(1, "First Name 1", "Last Name1"),
            new Author(2, "First Name 2", "Last Name 2"));

    private static final List<Genre> AVAILABLE_GENRES = List.of(
            new Genre(1, "Genre 1"),
            new Genre(2, "Genre 2"));

    private static final List<Comment> AVAILABLE_COMMENTS = new ArrayList<>(List.of(
            new Comment(1, "Comment 1"),
            new Comment(2, "Comment 2")
    ));
    private static final List<Book> AVAILABLE_BOOKS = Collections.singletonList(
            new Book(1, "Test Book", AVAILABLE_AUTHORS, AVAILABLE_GENRES, AVAILABLE_COMMENTS));

    @BeforeEach
    void setUp() {
        when(ioService.readStringWithPrompts("Enter book name:")).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains(" - "))).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Select"), any())).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Enter new book name"))).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains("Book"))).thenReturn("1");
        when(ioService.readStringWithPrompts(contains("Write comment "))).thenReturn("1");
        when(ioService.readStringWithPrompts(contains("Add comment "))).thenReturn("Test comment");
        when(ioService.readIntWithPrompts(any())).thenReturn(1);
        bookActionProcessor.setIoService(ioService);

        when(bookService.getAuthors()).thenReturn(AVAILABLE_AUTHORS);
        when(bookService.getGenres()).thenReturn(AVAILABLE_GENRES);
        when(bookService.getBooks()).thenReturn(AVAILABLE_BOOKS);
    }

    @Test
    void whenDoProcessCreate_thenNewBookIsSaved() {
        bookActionProcessor.processCreate();
        verify(bookService, times(1)).createBook(any());
    }

    @Test
    void whenDoProcessUpdate_thenBookIsUpdated() {
        bookActionProcessor.processUpdate();
        verify(bookService, times(1)).updateBook(any());
    }

    @Test
    void whenDoProcessDelete_thenBookIsDeleted() {
        bookActionProcessor.processDelete();
        verify(bookService, times(1)).deleteBook(any());
    }
}
