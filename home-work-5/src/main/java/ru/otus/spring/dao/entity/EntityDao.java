package ru.otus.spring.dao.entity;

import ru.otus.spring.model.entity.Entity;

import java.util.List;

public interface EntityDao<T extends Entity> {
    T getById(long id);
    void update(T entity);
    T save(T entity);
    void delete(T entity);
    List<T> getAll();
}
