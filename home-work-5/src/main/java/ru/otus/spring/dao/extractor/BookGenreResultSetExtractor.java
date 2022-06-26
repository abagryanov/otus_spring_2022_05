package ru.otus.spring.dao.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component("BookGenreResultSetExtractor")
public class BookGenreResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (resultSet.next()) {
            long bookId = resultSet.getLong("book_id");
            Book book = books.get(bookId);
            if (book == null) {
                book = new Book(
                        bookId,
                        "",
                        new ArrayList<>(),
                        new ArrayList<>());
                books.put(bookId, book);
            }
            book.getGenres().add(new Genre(
                    resultSet.getLong("genre_id"),
                    resultSet.getString("genre_name")));
        }
        return books;
    }
}
