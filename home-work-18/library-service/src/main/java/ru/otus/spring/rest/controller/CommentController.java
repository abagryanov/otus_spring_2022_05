package ru.otus.spring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.rest.dto.AddCommentRequestDto;
import ru.otus.spring.rest.dto.CommentsResponseDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final BookService bookService;

    @GetMapping("/api/comments/{id}")
    public CommentsResponseDto getComments(@PathVariable(name = "id") long id) {
        BookDto book = bookService.findBookById(id);
        List<CommentDto> comments = bookService.getBookCommentsDto(book);
        return new CommentsResponseDto(comments);
    }

    @DeleteMapping("/api/comments/{id}")
    public void deleteComment(@PathVariable(name = "id") long id) {
        bookService.deleteComment(new CommentDto(id));
    }

    @PostMapping("/api/comments/create")
    public void addComment(@RequestBody AddCommentRequestDto addCommentRequestDto) {
        long bookId = addCommentRequestDto.getBookId();
        String comment = addCommentRequestDto.getComment();
        BookDto book = bookService.findBookById(bookId);
        List<CommentDto> comments = book.getComments();
        comments.add(new CommentDto(comment));
        bookService.updateBook(book);
    }
}
