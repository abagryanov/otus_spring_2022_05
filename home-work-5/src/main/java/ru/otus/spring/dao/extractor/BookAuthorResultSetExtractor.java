package ru.otus.spring.dao.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component("BookAuthorResultSetExtractor")
public class BookAuthorResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (resultSet.next()) {
            long bookId = resultSet.getLong("book_id");
            Book book = books.get(bookId);
            if (book == null) {
                book = new Book(
                        bookId,
                        resultSet.getString("book_name"),
                        new ArrayList<>(),
                        new ArrayList<>());
                books.put(bookId, book);
            }
            book.getAuthors().add(new Author(
                    resultSet.getLong("author_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")));
        }
        return books;
    }
}
