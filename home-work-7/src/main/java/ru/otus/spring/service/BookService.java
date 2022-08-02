package ru.otus.spring.service;

import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;

import java.util.List;

public interface BookService {
    BookDto findBookById(long id);

    void createBook(BookDto bookDto);

    void updateBook(BookDto bookDto);

    void deleteBook(BookDto bookDto);

    List<BookDto> getBooksDto();

    List<AuthorDto> getAuthorsDto();

    List<GenreDto> getGenresDto();

    List<CommentDto> getBookCommentsDto(BookDto bookDto);

    void createComment(CommentDto commentDto);

    void deleteComment(CommentDto commentDto);
}
