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
import ru.otus.spring.model.mongo.BookDocument;
import ru.otus.spring.model.mongo.CommentDocument;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookDocumentCustomRepositoryImpl implements BookDocumentCustomRepository {
    private final MongoOperations mongoTemplate;

    private MongoCollection<Document> BookDocumentCollection;

    @Override
    public void addComment(BookDocument BookDocument, CommentDocument CommentDocument) {
        Document newCommentDocument = new Document("_id", new ObjectId(CommentDocument.getId()))
                .append("text", CommentDocument.getText());
        Bson filter = Filters.eq("_id", new ObjectId(BookDocument.getId()));
        Bson update = Updates.push("CommentDocuments", newCommentDocument);
        BookDocumentCollection.updateOne(filter, update);
    }

    @Override
    public void deleteComment(BookDocument BookDocument, CommentDocument CommentDocument) {
        Document CommentDocumentToDelete = new Document("_id", new ObjectId(CommentDocument.getId()));
        Bson filter = Filters.eq("_id", new ObjectId(BookDocument.getId()));
        Bson update = Updates.pull("CommentDocuments", CommentDocumentToDelete);
        BookDocumentCollection.updateOne(filter, update);
    }

    @Override
    public List<CommentDocument> getComments(BookDocument BookDocument) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("id").is(BookDocument.getId()))
                , unwind("CommentDocuments")
                , project().andExclude("_id")
                        .and("CommentDocuments.id").as("_id")
                        .and("CommentDocuments.text").as("text")
        );
        return mongoTemplate.aggregate(aggregation, BookDocument.class, CommentDocument.class).getMappedResults();
    }

    @PostConstruct
    private void init() {
        BookDocumentCollection = mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(BookDocument.class));
    }
}
