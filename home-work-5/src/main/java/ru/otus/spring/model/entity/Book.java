package ru.otus.spring.model.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book implements Entity {
    @EqualsAndHashCode.Include
    private long id;
    private String name;
    private List<Author> authors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();

    public Book(long id) {
        this.id = id;
    }

    public Book(long id,
                String name) {
        this.id = id;
        this.name = name;
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(String name,
                List<Author> authors,
                List<Genre> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }
}
