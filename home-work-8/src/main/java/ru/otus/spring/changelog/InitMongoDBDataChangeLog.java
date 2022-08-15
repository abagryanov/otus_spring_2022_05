package ru.otus.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @ChangeSet(order = "000", id = "dropDB", author = "abagryanov", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthor", author = "abagryanov", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        author1 = repository.save(new Author("Charles", "Bukowski"));
        author2 = repository.save(new Author("Alexander", "Pushkin"));
    }

    @ChangeSet(order = "002", id = "initGenre", author = "abagryanov", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genre1 = repository.save(new Genre("Fiction"));
        genre2 = repository.save(new Genre("Prose"));
    }

    @ChangeSet(order = "003", id = "initBook", author = "abagryanov", runAlways = true)
    public void initBooks(BookRepository repository) {
        List<Comment> firstBookComments = List.of(
                new Comment("Book about world and war"),
                new Comment("Book about love")
        );

        List<Comment> secondBookComments = List.of(
                new Comment("Book is awesome"),
                new Comment("My favourite book")
        );

        repository.save(new Book(
                "White book",
                List.of(author1, author2),
                List.of(genre1, genre2),
                firstBookComments));

        repository.save(new Book(
                "Black book",
                List.of(author2),
                List.of(genre1),
                secondBookComments
        ));
    }
}
