package ru.otus.spring.dao.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.relationship.BookGenreDao;
import ru.otus.spring.model.entity.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final RowMapper<Genre> genreRowMapper;

    private final BookGenreDao bookGenreDao;

    @Override
    public Genre getById(long id) {
        Map<String, Object> parameters = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select genre_id, name from genre where genre_id = :id",
                parameters, genreRowMapper);
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> parameters = Map.of(
                "id", genre.getId(),
                "name", genre.getName());
        namedParameterJdbcOperations.update("update genre set " +
                "name = :name " +
                "where genre_id = :id", parameters);
    }

    @Override
    @SuppressWarnings("all")
    public Genre save(Genre genre) {
        SqlParameterSource parameters = new MapSqlParameterSource(
                Collections.singletonMap(
                        "name", genre.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations
                .update("insert into genre (name) values (:name)",
                        parameters,
                        keyHolder);
        return getById(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public void delete(Genre genre) {
        bookGenreDao.deleteAllByDependent(genre);
        Map<String, Object> parameters = Collections.singletonMap("id", genre.getId());
        namedParameterJdbcOperations.update("delete from genre where genre_id = :id",
                parameters);
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select genre_id, name from genre",
                genreRowMapper);
    }
}
