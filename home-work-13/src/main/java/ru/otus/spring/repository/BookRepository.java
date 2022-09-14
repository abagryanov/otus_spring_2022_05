package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Book;

import java.util.List;

@Component
public interface BookRepository extends JpaRepository<Book, Long> {
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @PreAuthorize("#book.id == 0 || hasPermission(#book, 'WRITE')")
    Book save(Book book);
}
