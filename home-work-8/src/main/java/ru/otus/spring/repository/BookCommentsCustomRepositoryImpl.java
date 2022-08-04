package ru.otus.spring.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
public class BookCommentsCustomRepositoryImpl implements BookCommentsCustomRepository {
    private final MongoOperations mongoTemplate;

    private MongoCollection<Document> bookCollection;

    @Override
    public void addComment(Book book, Comment comment) {
        Document newComment = new Document("_id", new ObjectId(comment.getId()))
                .append("text", comment.getText());
        Bson filter = Filters.eq("_id", new ObjectId(book.getId()));
        Bson update = Updates.push("comments", newComment);
        bookCollection.updateOne(filter, update);
    }

    @Override
    public void deleteComment(Book book, Comment comment) {
        Document commentToDelete = new Document("_id", new ObjectId(comment.getId()));
        Bson filter = Filters.eq("_id", new ObjectId(book.getId()));
        Bson update = Updates.pull("comments", commentToDelete);
        bookCollection.updateOne(filter, update);
    }

    @Override
    public List<Comment> getComments(Book book) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("id").is(book.getId()))
                , unwind("comments")
                , project().andExclude("_id").and("comments.id").as("_id").and("comments.text").as("text")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }

    @PostConstruct
    private void init() {
        bookCollection = mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(Book.class));
    }
}
