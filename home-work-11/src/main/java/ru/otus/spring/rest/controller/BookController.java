package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.model.Book;
import ru.otus.spring.repository.BookRepository;

@RestController("bookController")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping("/api/books")
    public Flux<BookDto> getBooks() {
        return bookRepository.findAllWithoutComments();
    }

    @GetMapping("/api/books/{id}")
    public Mono<BookDto> getBook(@PathVariable(name = "id") String id) {
        return bookRepository.findByIdWithoutComments(id);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") String id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping("/api/books")
    public Mono<Book> createOrUpdateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
}
