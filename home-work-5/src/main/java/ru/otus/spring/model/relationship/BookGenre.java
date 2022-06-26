package ru.otus.spring.model.relationship;

import lombok.*;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookGenre implements Relationship<Book, Genre> {
    private long id;

    @EqualsAndHashCode.Include
    private Book book;

    @EqualsAndHashCode.Include
    private Genre genre;

    public BookGenre(Book book, Genre genre) {
        this.book = book;
        this.genre = genre;
    }
}
