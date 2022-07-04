package ru.otus.spring.repository;

import ru.otus.spring.model.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T extends Entity> {
    List<T> findAll();
    Optional<T> findById(long id);
    void delete(T entity);
    T save(T entity);
}
