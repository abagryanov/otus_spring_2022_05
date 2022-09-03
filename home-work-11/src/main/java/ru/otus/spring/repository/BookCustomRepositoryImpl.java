package ru.otus.spring.repository;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {
    private final ReactiveMongoOperations mongoTemplate;

    @Override
    public Mono<BookDto> findByIdWithoutComments(String id) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("id").is(id)),
                project().andExclude("comments")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Book.class)
                .map(this::toMonoBookDto)
                .flatMap(x -> x.flatMapMany(Flux::just))
                .elementAt(0);
    }

    @Override
    public Flux<BookDto> findAllWithoutComments() {
        Aggregation aggregation = Aggregation.newAggregation(
                project().andExclude("comments")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Book.class)
                .map(this::toMonoBookDto)
                .flatMap(x -> x.flatMapMany(Flux::just));
    }

    private Mono<BookDto> toMonoBookDto(Book book) {
        Query authorsQuery = new Query();
        authorsQuery.addCriteria(
                Criteria.where("_id").in(book.getAuthors())
        );
        Query genresQuery = new Query();
        genresQuery.addCriteria(
                Criteria.where("_id").in(book.getGenres())
        );
        return Mono.zip(mongoTemplate.find(authorsQuery, Author.class).collectList(),
                        mongoTemplate.find(genresQuery, Genre.class).collectList())
                .map(tuple -> new BookDto(
                        book.getId(),
                        book.getName(),
                        tuple.getT1(),
                        tuple.getT2()
                ));
    }

    @Override
    public Mono<UpdateResult> addComment(String bookId, Comment comment) {
        Document newComment = new Document("_id", new ObjectId(comment.getId()))
                .append("text", comment.getText());
        Query query = new Query();
        query.addCriteria(
                Criteria.where("_id")
                        .is(new ObjectId(bookId))
        );
        Update update = new Update();
        update.push("comments", newComment);
        return mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public Mono<UpdateResult> deleteComment(String bookId, String commentId) {
        Document commentToDelete = new Document("_id", new ObjectId(commentId));
        Query query = new Query();
        query.addCriteria(
                Criteria.where("_id")
                        .is(new ObjectId(bookId))
        );
        Update update = new Update();
        update.pull("comments", commentToDelete);
        return mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public Flux<Comment> getCommentsByBookId(String bookId) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("comments")
                , project().andExclude("_id")
                        .and("comments._id").as("_id")
                        .and("comments.text").as("text")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class);
    }
}
