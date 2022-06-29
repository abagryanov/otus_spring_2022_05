package ru.otus.spring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;
import ru.otus.spring.model.relationship.BookGenre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookGenreMapper implements RowMapper<BookGenre> {
    @Override
    public BookGenre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("book_genre_id");
        long bookId = resultSet.getLong("book_id");
        long genre_id = resultSet.getLong("genre_id");
        return new BookGenre(id, new Book(bookId), new Genre(genre_id));
    }
}
