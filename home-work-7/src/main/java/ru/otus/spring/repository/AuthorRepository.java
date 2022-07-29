package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Author;

@Component
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
