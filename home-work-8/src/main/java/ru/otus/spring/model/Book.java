package ru.otus.spring.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "book")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
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
    private List<Author> authors = new ArrayList<>();

    @Getter
    @Setter
    private List<Genre> genres = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public Book(String name,
                List<Author> authors,
                List<Genre> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String name,
                List<Author> authors,
                List<Genre> genres,
                List<Comment> comments) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public Book(String name) {
        this.name = name;
    }
}
