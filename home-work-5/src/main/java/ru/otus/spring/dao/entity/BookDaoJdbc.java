package ru.otus.spring.dao.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.constant.Query;
import ru.otus.spring.dao.relationship.BookAuthorDao;
import ru.otus.spring.dao.relationship.BookGenreDao;
import ru.otus.spring.model.entity.Book;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Qualifier("BookAuthorResultSetExtractor")
    private final ResultSetExtractor<Map<Long, Book>> bookAuthorResultSetExtractor;

    @Qualifier("BookGenreResultSetExtractor")
    private final ResultSetExtractor<Map<Long, Book>> bookGenreResultSetExtractor;

    private final BookAuthorDao bookAuthorDao;

    private final BookGenreDao bookGenreDao;

    @Override
    public Book getById(long id) {
        Map<String, Object> parameters = Collections.singletonMap("id", id);
        Map<Long, Book> bookAuthorsResult = namedParameterJdbcOperations.query(
                Query.CURRENT_BOOK_AUTHORS_QUERY,
                parameters, bookAuthorResultSetExtractor);
        Map<Long, Book> bookGenresResult = namedParameterJdbcOperations.query(
                Query.CURRENT_BOOK_GENRES_QUERY,
                parameters, bookGenreResultSetExtractor);
        List<Book> books = mergeExtractorResultsAndGet(bookAuthorsResult, bookGenresResult);
        return books.isEmpty() ? new Book() : books.get(0);
    }

    @Override
    public void update(Book book) {
        Map<String, Object> parameters = Map.of(
                "id", book.getId(),
                "name", book.getName());
        namedParameterJdbcOperations.update("update book set " +
                "name = :name " +
                "where book_id = :id", parameters);
        bookAuthorDao.updateAllByHolder(book);
        bookGenreDao.updateAllByHolder(book);
    }

    @Override
    @SuppressWarnings("all")
    public Book save(Book book) {
        SqlParameterSource parameters = new MapSqlParameterSource(
                Collections.singletonMap(
                        "name", book.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations
                .update("insert into book (name) " +
                                "values (:name)",
                        parameters,
                        keyHolder);
        long bookId = keyHolder.getKeyAs(Long.class);
        book.setId(bookId);
        bookAuthorDao.saveAllByHolder(book);
        bookGenreDao.saveAllByHolder(book);
        return getById(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public void delete(Book book) {
        bookAuthorDao.deleteAllByHolder(book);
        bookGenreDao.deleteAllByHolder(book);
        Map<String, Object> parameters = Collections.singletonMap("id", book.getId());
        namedParameterJdbcOperations.update("delete from book where book_id = :id",
                parameters);
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Book> booksAuthorsResult = namedParameterJdbcOperations.query(
                Query.ALL_BOOKS_AUTHORS_QUERY, bookAuthorResultSetExtractor);
        Map<Long, Book> booksGenresResult = namedParameterJdbcOperations.query(
                Query.ALL_BOOKS_GENRES_QUERY, bookGenreResultSetExtractor);
        return mergeExtractorResultsAndGet(booksAuthorsResult, booksGenresResult);
    }

    @SafeVarargs
    private List<Book> mergeExtractorResultsAndGet(Map<Long, Book>... extractorResults) {
        Map<Long, Book> result = new HashMap<>();
        Arrays.stream(extractorResults)
                .forEach(extractorResult -> extractorResult.forEach((bookId, book) -> {
                    Book mergedBook = result.get(bookId);
                    if (mergedBook == null) {
                        result.put(bookId, book);
                    } else {
                        mergedBook.getAuthors().addAll(book.getAuthors());
                        mergedBook.getGenres().addAll(book.getGenres());
                        String mergedBookName = mergedBook.getName();
                        if (mergedBookName.isEmpty()) {
                            mergedBook.setName(book.getName());
                        }
                    }
                }));
        return new ArrayList<>(result.values());
    }
}
