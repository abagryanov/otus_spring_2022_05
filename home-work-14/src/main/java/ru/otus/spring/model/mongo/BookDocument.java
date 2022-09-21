package ru.otus.spring.model.mongo;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "book")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class BookDocument {
    @Id
    @EqualsAndHashCode.Include
    @Getter
    private String id;

    @Field(name = "name")
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @DBRef
    private List<AuthorDocument> authors = new ArrayList<>();

    @Getter
    @Setter
    @DBRef
    private List<GenreDocument> genres = new ArrayList<>();

    private List<CommentDocument> comments = new ArrayList<>();

    public BookDocument(String name,
                List<AuthorDocument> authors,
                List<GenreDocument> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public BookDocument(String name,
                List<AuthorDocument> authors,
                List<GenreDocument> genres,
                List<CommentDocument> comments) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public BookDocument(String name) {
        this.name = name;
    }
}
