package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private final EntityManager entityManager;

    @Override
    public List<Comment> findAll() {
        var query = entityManager.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAllByBook(Book book) {
        var query = entityManager.createQuery("select c from Comment c where c.book = :book",
                Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
         return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public void delete(Comment comment) {
        comment = entityManager.contains(comment) ? comment : entityManager.merge(comment);
        entityManager.remove(comment);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }
}
