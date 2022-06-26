package ru.otus.spring.dao.relationship;

import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Genre;
import ru.otus.spring.model.relationship.BookGenre;

public interface BookGenreDao extends RelationshipDao<Book, Genre, BookGenre>{
}
