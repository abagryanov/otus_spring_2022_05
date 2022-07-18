package ru.otus.spring.service.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.validator.UserInputValidatorImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookActionConsoleProcessor.class, UserInputValidatorImpl.class})
@Import({BookActionConsoleProcessor.class, UserInputValidatorImpl.class})
public class BookActionConsoleProcessorTest {
    @Autowired
    private BookActionProcessor bookActionProcessor;

    @MockBean
    private IoService ioService;

    @MockBean
    private BookService bookService;

    private static final List<AuthorDto> AVAILABLE_AUTHORS = List.of(
            new AuthorDto(1, "First Name 1", "Last Name1"),
            new AuthorDto(2, "First Name 2", "Last Name 2"));

    private static final List<GenreDto> AVAILABLE_GENRES = List.of(
            new GenreDto(1, "Genre 1"),
            new GenreDto(2, "Genre 2"));

    private static final List<BookDto> AVAILABLE_BOOKS = Collections.singletonList(
            new BookDto(1, "Test Book", AVAILABLE_AUTHORS, AVAILABLE_GENRES));

    @BeforeEach
    void setUp() {
        when(ioService.readStringWithPrompts("Enter book name:")).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains(" - "))).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Select"), any())).thenReturn("0,1");
        when(ioService.readStringWithPrompts(contains("Enter new book name"))).thenReturn("Test");
        when(ioService.readStringWithPrompts(contains("Book"))).thenReturn("1");
        when(ioService.readIntWithPrompts(any())).thenReturn(1);

        when(bookService.getAuthorsDto()).thenReturn(AVAILABLE_AUTHORS);
        when(bookService.getGenresDto()).thenReturn(AVAILABLE_GENRES);
        when(bookService.getBooksDto()).thenReturn(AVAILABLE_BOOKS);
        when(bookService.findBookById(anyLong())).thenReturn(AVAILABLE_BOOKS.get(0));
    }

    @Test
    void whenDoProcessCreate_thenNewBookIsSaved() {
        bookActionProcessor.processCreate();
        verify(bookService, times(1)).createBook(any());
    }

    @Test
    void whenDoProcessUpdate_thenBookIsUpdated() {
        bookActionProcessor.processUpdate(1);
        verify(bookService, times(1)).updateBook(any());
    }

    @Test
    void whenDoProcessDelete_thenBookIsDeleted() {
        bookActionProcessor.processDelete(1);
        verify(bookService, times(1)).deleteBook(any());
    }
}
