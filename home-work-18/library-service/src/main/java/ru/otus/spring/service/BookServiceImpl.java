package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

   // @HystrixCommand(commandKey = "getBooksData", fallbackMethod = "getBooksDtoStub")
    // Не работает ленивая загрузка данных не работает и кидается исключение
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getBooksDto() {
        return converter.toBooksDto(bookRepository.findAll());
    }

    @HystrixCommand(commandKey = "getBooksData", fallbackMethod = "getAuthorsDtoStub")
    @Override
    public List<AuthorDto> getAuthorsDto() {
        return converter.toAuthorsDto(authorRepository.findAll());
    }

    @HystrixCommand(commandKey = "getBooksData", fallbackMethod = "getGenresDtoStub")
    @Override
    public List<GenreDto> getGenresDto() {
        return converter.toGenresDto(genreRepository.findAll());
    }

    @HystrixCommand(commandKey = "getBooksData", fallbackMethod = "getBookCommentsDtoStub")
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

    @SuppressWarnings("unused")
    private List<BookDto> getBooksDtoStub() {
        log.info("Fall back method for books data");
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    private List<AuthorDto> getAuthorsDtoStub() {
        log.info("Fall back method for authors data");
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    private List<GenreDto> getGenresDtoStub() {
        log.info("Fall back method for genres data");
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    private List<CommentDto> getBookCommentsDtoStub(BookDto book) {
        log.info("Fall back method for book comments data");
        return Collections.emptyList();
    }
}
