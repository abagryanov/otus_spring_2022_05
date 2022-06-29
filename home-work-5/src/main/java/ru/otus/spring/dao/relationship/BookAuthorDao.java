package ru.otus.spring.dao.relationship;

import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.relationship.BookAuthor;

public interface BookAuthorDao extends RelationshipDao<Book, Author, BookAuthor> {
}
