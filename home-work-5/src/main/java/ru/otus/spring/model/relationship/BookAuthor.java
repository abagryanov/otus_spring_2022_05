package ru.otus.spring.model.relationship;

import lombok.*;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookAuthor implements Relationship<Book, Author> {
    private long id;
    @EqualsAndHashCode.Include
    private Book book;
    @EqualsAndHashCode.Include
    private Author author;

    public BookAuthor(Book book, Author author) {
        this.book = book;
        this.author = author;
    }
}
