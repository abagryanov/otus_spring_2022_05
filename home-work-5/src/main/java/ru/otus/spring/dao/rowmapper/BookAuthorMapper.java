package ru.otus.spring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.relationship.BookAuthor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookAuthorMapper implements RowMapper<BookAuthor> {
    @Override
    public BookAuthor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("book_author_id");
        long bookId = resultSet.getLong("book_id");
        long author_id = resultSet.getLong("author_id");
        return new BookAuthor(id, new Book(bookId), new Author(author_id));
    }
}
