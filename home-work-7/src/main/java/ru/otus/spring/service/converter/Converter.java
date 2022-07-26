package ru.otus.spring.service.converter;

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

public interface Converter {
    List<BookDto> toBooksDto(Collection<Book> books);

    List<Book> toBooks(Collection<BookDto> booksDto);

    Book toBook(BookDto bookDto);

    BookDto toBookDto(Book book);

    List<AuthorDto> toAuthorsDto(Collection<Author> authors);

    List<GenreDto> toGenresDto(Collection<Genre> genres);

    List<Author> toAuthors(Collection<AuthorDto> authorsDto);

    List<Genre> toGenres(Collection<GenreDto> genresDto);

    List<Comment> toComments(Collection<CommentDto> commentsDto);

    Comment toComment(CommentDto commentDto);

    List<CommentDto> toCommentsDto(Collection<Comment> comments);
}
