package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Book;

@Component
public interface BookRepository extends JpaRepository<Book, Long> {
}
