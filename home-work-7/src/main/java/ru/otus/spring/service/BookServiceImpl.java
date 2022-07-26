package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.model.Book;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.converter.Converter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final Converter converter;

    @Transactional
    @Override
    public BookDto findBookById(long id) {
        return converter.toBookDto(bookRepository.findById(id).orElse(new Book()));
    }

    @Transactional
    @Override
    public void createBook(BookDto bookDto) {
        bookRepository.save(
                converter.toBook(bookDto));
    }

    @Transactional
    @Override
    public void updateBook(BookDto bookDto) {
        bookRepository.save(converter.toBook(bookDto));
    }

    @Transactional
    @Override
    public void deleteBook(BookDto bookDto) {
        bookRepository.delete(converter.toBook(bookDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getBooksDto() {
        return converter.toBooksDto(bookRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> getAuthorsDto() {
        return converter.toAuthorsDto(authorRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getGenresDto() {
        return converter.toGenresDto(genreRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getBookCommentsDto(BookDto bookDto) {
        return bookDto.getComments();
    }

    @Transactional
    @Override
    public void createComment(CommentDto commentDto) {
        commentRepository.save(converter.toComment(commentDto));
    }

    @Transactional
    @Override
    public void deleteComment(CommentDto commentDto) {
        commentRepository.delete(converter.toComment(commentDto));
    }
}
