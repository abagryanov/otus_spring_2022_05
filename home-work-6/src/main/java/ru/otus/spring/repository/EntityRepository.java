package ru.otus.spring.repository;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {
    List<T> findAll();
    Optional<T> findById(long id);
    void delete(T entity);
    T save(T entity);
}
