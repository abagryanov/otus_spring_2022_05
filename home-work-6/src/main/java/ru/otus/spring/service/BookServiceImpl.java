package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }
}
