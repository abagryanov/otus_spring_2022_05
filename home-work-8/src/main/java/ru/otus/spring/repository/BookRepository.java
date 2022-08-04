package ru.otus.spring.repository;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Book;

@Component
public interface BookRepository extends Repository<Book, String>, BookCustomRepository, BookCommentsCustomRepository {
}
