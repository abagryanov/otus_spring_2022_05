package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Genre;

@Component
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
