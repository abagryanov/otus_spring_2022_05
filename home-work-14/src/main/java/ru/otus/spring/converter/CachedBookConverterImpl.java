package ru.otus.spring.converter;

import org.bson.types.ObjectId;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.mongo.AuthorDocument;
import ru.otus.spring.model.mongo.BookDocument;
import ru.otus.spring.model.mongo.CommentDocument;
import ru.otus.spring.model.mongo.GenreDocument;
import ru.otus.spring.model.sql.Author;
import ru.otus.spring.model.sql.Book;
import ru.otus.spring.model.sql.Comment;
import ru.otus.spring.model.sql.Genre;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@StepScope
@Component
public class CachedBookConverterImpl implements CachedBookConverter {
    private final Map<Long, AuthorDocument> AUTHORS_CONVERTER_CACHE = new ConcurrentHashMap<>();
    private final Map<Long, GenreDocument> GENRES_CONVERTER_CACHE = new ConcurrentHashMap<>();

    @Override
    public BookDocument convert(Book book) {
        return new BookDocument(
                new ObjectId().toHexString(),
                book.getName(),
                convertAuthors(book.getAuthors()),
                convertGenres(book.getGenres()),
                convertComments(book.getComments()));
    }

    private List<AuthorDocument> convertAuthors(List<Author> authors) {
        return authors.stream()
                .map(author -> {
                    AuthorDocument cachedAuthorDocument = AUTHORS_CONVERTER_CACHE.get(author.getId());
                    if (cachedAuthorDocument == null) {
                        AuthorDocument authorDocument = new AuthorDocument(
                                new ObjectId().toHexString(),
                                author.getFirstName(),
                                author.getLastName());
                        AUTHORS_CONVERTER_CACHE.put(author.getId(), authorDocument);
                        return authorDocument;
                    } else {
                        return cachedAuthorDocument;
                    }
                }).collect(Collectors.toList());
    }

    private List<GenreDocument> convertGenres(List<Genre> genres) {
        return genres.stream()
                .map(genre -> {
                    GenreDocument cachedGenreDocument = GENRES_CONVERTER_CACHE.get(genre.getId());
                    if (cachedGenreDocument == null) {
                        GenreDocument genreDocument = new GenreDocument(
                                new ObjectId().toHexString(),
                                genre.getName());
                        GENRES_CONVERTER_CACHE.put(genre.getId(), genreDocument);
                        return genreDocument;
                    } else {
                        return cachedGenreDocument;
                    }
                }).collect(Collectors.toList());
    }

    private List<CommentDocument> convertComments(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new CommentDocument(new ObjectId().toHexString(), comment.getComment()))
                .collect(Collectors.toList());
    }
}
