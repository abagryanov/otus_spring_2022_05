package ru.otus.spring.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {@NamedAttributeNode("comments")})
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "name")
    private String name;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private List<Author> authors = new ArrayList<>();

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres = new ArrayList<>();

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "book_id")
    private List<Comment> comments = new ArrayList<>();

    public Book(String name,
                List<Author> authors,
                List<Genre> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(long id) {
        this.id = id;
    }
}
