package ru.otus.spring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("author_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        return new Author(id, firstName, lastName);
    }
}
