package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.model.Book;
import ru.otus.spring.permission.PermissionService;
import ru.otus.spring.rest.dto.BookResponseDto;
import ru.otus.spring.rest.dto.BookUpdateRequestDto;
import ru.otus.spring.rest.dto.BooksResponseDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private final PermissionService permissionService;

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
    public void create(@RequestBody BookUpdateRequestDto bookUpdateRequestDto,
                       Authentication authentication) {
        String userLogin = authentication.getName();
        BookDto bookDto = bookService.createBook(bookUpdateRequestDto);
        permissionService.addPermissionForUser(Book.class, bookDto.getId(), BasePermission.READ, userLogin);
        permissionService.addPermissionForUser(Book.class, bookDto.getId(), BasePermission.WRITE, userLogin);
    }

    @PostMapping("/api/books/edit")
    public void edit(@RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        bookService.updateBook(bookUpdateRequestDto);
    }
}
