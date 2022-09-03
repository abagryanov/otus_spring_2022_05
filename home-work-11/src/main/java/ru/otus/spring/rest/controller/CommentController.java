package ru.otus.spring.rest.controller;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.model.Comment;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.rest.dto.AddCommentRequestDto;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final BookRepository bookRepository;

    @GetMapping("/api/books/{bookId}/comments")
    public Flux<Comment> getComments(@PathVariable(name = "bookId") String bookId) {
        return bookRepository.getCommentsByBookId(bookId);
    }

    @DeleteMapping("/api/books/{bookId}/comments/{commentId}")
    public Mono<UpdateResult> deleteComment(@PathVariable(name = "bookId") String bookId,
                                            @PathVariable(name = "commentId") String commentId) {
        return bookRepository.deleteComment(bookId, commentId);
    }

    @PostMapping("/api/books/comments")
    public Mono<UpdateResult> addComment(@RequestBody AddCommentRequestDto addCommentRequestDto) {
        String bookId = addCommentRequestDto.getBookId();
        String comment = addCommentRequestDto.getComment();
        return bookRepository.addComment(bookId, new Comment(comment));
    }
}
