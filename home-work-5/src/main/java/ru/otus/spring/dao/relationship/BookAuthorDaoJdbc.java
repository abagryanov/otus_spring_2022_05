package ru.otus.spring.dao.relationship;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.relationship.BookAuthor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookAuthorDaoJdbc implements BookAuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final RowMapper<BookAuthor> bookAuthorRowMapper;

    @Override
    public BookAuthor getById(long bookAuthorId) {
        Map<String, Object> parameters = Collections.singletonMap("id", bookAuthorId);
        return namedParameterJdbcOperations.queryForObject(
                "select book_author_id, book_id, author_id from book_author " +
                        "where book_author_id = :id",
                parameters, bookAuthorRowMapper);
    }

    @Override
    public List<BookAuthor> getAllByHolder(Book book) {
        Map<String, Object> parameters = Collections.singletonMap("bookId", book.getId());
        return namedParameterJdbcOperations.query(
                "select book_author_id, book_id, author_id from book_author " +
                        "where book_id = :bookId",
                parameters,
                bookAuthorRowMapper);
    }

    @Override
    public List<BookAuthor> updateAllByHolder(Book book) {
        List<BookAuthor> currentRelationships = getAllByHolder(book);
        List<BookAuthor> newRelationships = toRelationships(book);
        List<BookAuthor> relationshipsToDelete = new ArrayList<>(currentRelationships);
        relationshipsToDelete.removeAll(newRelationships);
        relationshipsToDelete.forEach(this::delete);
        List<BookAuthor> relationshipsToAdd = new ArrayList<>(newRelationships);
        relationshipsToAdd.removeAll(currentRelationships);
        relationshipsToAdd.forEach(this::save);
        return getAllByHolder(book);
    }

    @Override
    public void delete(BookAuthor bookAuthor) {
        Map<String, Object> parameters = Collections.singletonMap("id", bookAuthor.getId());
        namedParameterJdbcOperations.update(
                "delete from book_author where book_author_id = :id",
                parameters);
    }

    @Override
    public void deleteAllByHolder(Book book) {
        Map<String, Object> parameters = Collections.singletonMap("id", book.getId());
        namedParameterJdbcOperations.update(
                "delete from book_author where book_id = :id",
                parameters);
    }

    @Override
    public void deleteAllByDependent(Author author) {
        Map<String, Object> parameters = Collections.singletonMap("id", author.getId());
        namedParameterJdbcOperations.update(
                "delete from book_author where author_id = :id",
                parameters);
    }

    @Override
    @SuppressWarnings("all")
    public BookAuthor save(BookAuthor bookAuthor) {
        SqlParameterSource parameters = new MapSqlParameterSource(Map.of(
                "bookId", bookAuthor.getBook().getId(),
                "authorId", bookAuthor.getAuthor().getId()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations
                .update("insert into book_author (book_id, author_id) " +
                                "values (:bookId, :authorId)",
                        parameters,
                        keyHolder);
        return getById(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public List<BookAuthor> saveAllByHolder(Book book) {
        List<BookAuthor> bookAuthors = toRelationships(book);
        return bookAuthors.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    private List<BookAuthor> toRelationships(Book book) {
        List<Author> authors = book.getAuthors();
        return authors.stream()
                .map(author -> new BookAuthor(book, author))
                .collect(Collectors.toList());
    }
}
