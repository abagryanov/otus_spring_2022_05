package ru.otus.spring.converter;

import ru.otus.spring.model.mongo.BookDocument;
import ru.otus.spring.model.sql.Book;

import java.util.List;

public interface CachedBookConverter {
    BookDocument convert(Book book);
}
