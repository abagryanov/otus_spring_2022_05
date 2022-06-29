package ru.otus.spring.dao.relationship;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;
import ru.otus.spring.model.relationship.BookGenre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookGenreDaoJdbc implements BookGenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final RowMapper<BookGenre> bookGenreRowMapper;

    @Override
    public BookGenre getById(long bookGenreId) {
        Map<String, Object> parameters = Collections.singletonMap("id", bookGenreId);
        return namedParameterJdbcOperations.queryForObject(
                "select book_genre_id, book_id, genre_id from book_genre " +
                        "where book_genre_id = :id",
                parameters, bookGenreRowMapper);
    }

    @Override
    public List<BookGenre> getAllByHolder(Book book) {
        Map<String, Object> parameters = Collections.singletonMap("bookId", book.getId());
        return namedParameterJdbcOperations.query(
                "select book_genre_id, book_id, genre_id from book_genre " +
                        "where book_id = :bookId",
                parameters,
                bookGenreRowMapper);
    }

    @Override
    public List<BookGenre> updateAllByHolder(Book book) {
        List<BookGenre> currentRelationships = getAllByHolder(book);
        List<BookGenre> newRelationships = toRelationships(book);
        List<BookGenre> relationshipsToDelete = new ArrayList<>(currentRelationships);
        relationshipsToDelete.removeAll(newRelationships);
        relationshipsToDelete.forEach(this::delete);
        List<BookGenre> relationshipsToAdd = new ArrayList<>(newRelationships);
        relationshipsToAdd.removeAll(currentRelationships);
        relationshipsToAdd.forEach(this::save);
        return getAllByHolder(book);
    }

    @Override
    public void delete(BookGenre bookGenre) {
        Map<String, Object> parameters = Collections.singletonMap("id", bookGenre.getId());
        namedParameterJdbcOperations.update(
                "delete from book_genre where book_genre_id = :id",
                parameters);
    }

    @Override
    public void deleteAllByHolder(Book book) {
        Map<String, Object> parameters = Collections.singletonMap("id", book.getId());
        namedParameterJdbcOperations.update(
                "delete from book_genre where book_id = :id",
                parameters);
    }

    @Override
    public void deleteAllByDependent(Genre genre) {
        Map<String, Object> parameters = Collections.singletonMap("id", genre.getId());
        namedParameterJdbcOperations.update(
                "delete from book_genre where genre_id = :id",
                parameters);
    }

    @Override
    @SuppressWarnings("all")
    public BookGenre save(BookGenre bookGenre) {
        SqlParameterSource parameters = new MapSqlParameterSource(Map.of(
                "bookId", bookGenre.getBook().getId(),
                "genreId", bookGenre.getGenre().getId()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations
                .update("insert into book_genre (book_id, genre_id) " +
                                "values (:bookId, :genreId)",
                        parameters,
                        keyHolder);
        return getById(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public List<BookGenre> saveAllByHolder(Book book) {
        List<BookGenre> bookGenres = toRelationships(book);
        return bookGenres.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    private List<BookGenre> toRelationships(Book book) {
        List<Genre> genres = book.getGenres();
        return genres.stream()
                .map(genre -> new BookGenre(book, genre))
                .collect(Collectors.toList());
    }
}
