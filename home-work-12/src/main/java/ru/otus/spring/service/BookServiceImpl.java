package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.rest.dto.BookUpdateRequestDto;
import ru.otus.spring.service.converter.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final Converter converter;

    @Transactional(readOnly = true)
    @Override
    public BookDto findBookById(long id) {
        return converter.toBookDto(bookRepository.findById(id).orElse(new Book()));
    }

    @Override
    public void createBook(BookDto bookDto) {
        bookRepository.save(
                converter.toBook(bookDto));
    }

    @Override
    public void createBook(BookUpdateRequestDto bookUpdateRequestDto) {
        Book book = new Book();
        book.setName(bookUpdateRequestDto.getName());
        book.setAuthors(getAuthors(bookUpdateRequestDto));
        book.setGenres(getGenres(bookUpdateRequestDto));
        bookRepository.save(book);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        bookRepository.save(converter.toBook(bookDto));
    }

    @Transactional
    @Override
    public void updateBook(BookUpdateRequestDto bookUpdateRequestDto) {
        Book book = bookRepository.findById(bookUpdateRequestDto.getId())
                .orElse(new Book());
        book.setName(bookUpdateRequestDto.getName());
        book.setAuthors(getAuthors(bookUpdateRequestDto));
        book.setGenres(getGenres(bookUpdateRequestDto));
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(BookDto bookDto) {
        bookRepository.delete(converter.toBook(bookDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getBooksDto() {
        return converter.toBooksDto(bookRepository.findAll());
    }

    @Override
    public List<AuthorDto> getAuthorsDto() {
        return converter.toAuthorsDto(authorRepository.findAll());
    }

    @Override
    public List<GenreDto> getGenresDto() {
        return converter.toGenresDto(genreRepository.findAll());
    }

    @Override
    public List<CommentDto> getBookCommentsDto(BookDto bookDto) {
        return bookDto.getComments();
    }

    @Override
    public void createComment(CommentDto commentDto) {
        commentRepository.save(converter.toComment(commentDto));
    }

    @Override
    public void deleteComment(CommentDto commentDto) {
        commentRepository.delete(converter.toComment(commentDto));
    }

    private List<Author> getAuthors(BookUpdateRequestDto bookUpdateRequestDto) {
        return bookUpdateRequestDto.getAuthors().stream()
                .map(authorRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<Genre> getGenres(BookUpdateRequestDto bookUpdateRequestDto) {
        return bookUpdateRequestDto.getGenres().stream()
                .map(genreRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
