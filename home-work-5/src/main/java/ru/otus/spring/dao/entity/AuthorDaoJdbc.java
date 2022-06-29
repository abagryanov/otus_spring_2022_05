package ru.otus.spring.dao.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.relationship.BookAuthorDao;
import ru.otus.spring.model.entity.Author;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final RowMapper<Author> authorRowMapper;

    private final BookAuthorDao bookAuthorDao;

    @Override
    public Author getById(long id) {
        Map<String, Object> parameters = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select author_id, first_name, last_name from author where author_id = :id",
                parameters, authorRowMapper);
    }

    @Override
    public void update(Author author) {
        Map<String, Object> parameters = Map.of(
                "id", author.getId(),
                "firstName", author.getFirstName(),
                "lastName", author.getLastName());
        namedParameterJdbcOperations.update("update author set " +
                "first_name = :firstName, " +
                "last_name = :lastName " +
                "where author_id = :id", parameters);
    }

    @Override
    @SuppressWarnings("all")
    public Author save(Author author) {
        SqlParameterSource parameters = new MapSqlParameterSource(Map.of(
                "firstName", author.getFirstName(),
                "lastName", author.getLastName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations
                .update("insert into author (first_name, last_name) " +
                                "values (:firstName, :lastName)",
                        parameters,
                        keyHolder);
        return getById(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public void delete(Author author) {
        bookAuthorDao.deleteAllByDependent(author);
        Map<String, Object> parameters = Collections.singletonMap("id", author.getId());
        namedParameterJdbcOperations.update("delete from author where author_id = :id",
                parameters);
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query(
                "select author_id, first_name, last_name from author",
                authorRowMapper);
    }
}
