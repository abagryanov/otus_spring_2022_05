package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.rest.dto.BookResponseDto;
import ru.otus.spring.rest.dto.BookUpdateRequestDto;
import ru.otus.spring.rest.dto.BooksResponseDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public BooksResponseDto getBooks() {
        List<BookDto> books = bookService.getBooksDto();
        return new BooksResponseDto(books);
    }

    @GetMapping("/api/books/{id}")
    public BookResponseDto getBook(@PathVariable(name = "id") long id) {
        BookDto book = bookService.findBookById(id);
        return new BookResponseDto(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        bookService.deleteBook(new BookDto(id));
    }

    @PostMapping("/api/books/create")
    public void create(@RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        bookService.createBook(bookUpdateRequestDto);
    }

    @PostMapping("/api/books/edit")
    public void edit(@RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        bookService.updateBook(bookUpdateRequestDto);
    }
}
