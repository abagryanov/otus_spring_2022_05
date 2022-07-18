package ru.otus.spring.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterImpl implements Converter {
    @Override
    public List<BookDto> toBooksDto(Collection<Book> books) {
        return books.stream()
                .map(this::toBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> toBooks(Collection<BookDto> booksDto) {
        return booksDto.stream()
                .map(this::toBook)
                .collect(Collectors.toList());
    }

    @Override
    public Book toBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                toAuthors(bookDto.getAuthors()),
                toGenres(bookDto.getGenres()));
    }

    @Override
    public BookDto toBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                toAuthorsDto(book.getAuthors()),
                toGenresDto(book.getGenres()));
    }

    @Override
    public List<AuthorDto> toAuthorsDto(Collection<Author> authors) {
        return authors.stream()
                .map(author -> new AuthorDto(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> toGenresDto(Collection<Genre> genres) {
        return genres.stream()
                .map(genre -> new GenreDto(
                        genre.getId(),
                        genre.getName()
                )).collect(Collectors.toList());
    }

    @Override
    public List<Author> toAuthors(Collection<AuthorDto> authorsDto) {
        return authorsDto.stream()
                .map(authorDto -> new Author(
                        authorDto.getId(),
                        authorDto.getFirstName(),
                        authorDto.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> toGenres(Collection<GenreDto> genresDto) {
        return genresDto.stream()
                .map(genreDto -> new Genre(
                        genreDto.getId(),
                        genreDto.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> toCommentsDto(Collection<Comment> comments) {
        return comments.stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getComment(),
                        toBookDto(comment.getBook())))
                .collect(Collectors.toList());
    }

    @Override
    public Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getComment(),
                toBook(commentDto.getBook()));
    }
}
