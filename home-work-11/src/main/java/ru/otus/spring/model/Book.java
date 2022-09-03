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
@ToString
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
    private List<String> authors = new ArrayList<>();

    @Getter
    @Setter
    private List<String> genres = new ArrayList<>();

    private List<String> comments = new ArrayList<>();

    public Book(String name,
                List<String> authors,
                List<String> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String id,
                String name,
                List<String> authors,
                List<String> genres) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String name) {
        this.name = name;
    }
}
