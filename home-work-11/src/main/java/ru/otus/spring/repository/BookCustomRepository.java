package ru.otus.spring.repository;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.model.Comment;

public interface BookCustomRepository {
    Mono<BookDto> findByIdWithoutComments(String bookId);

    Flux<BookDto> findAllWithoutComments();

    Mono<UpdateResult> addComment(String bookId, Comment comment);

    Mono<UpdateResult> deleteComment(String bookId, String commentId);

    Flux<Comment> getCommentsByBookId(String bookId);
}
