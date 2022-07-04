package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.model.Description;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DescriptionRepositoryJpa implements DescriptionRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Description> findAll() {
        var query = entityManager.createQuery("select d from Description d", Description.class);
        return query.getResultList();
    }

    @Override
    public Optional<Description> findById(long id) {
         return Optional.ofNullable(entityManager.find(Description.class, id));
    }

    @Override
    public void delete(Description description) {
        entityManager.remove(description);
    }

    @Override
    public Description save(Description description) {
        if (description.getId() == 0) {
            entityManager.persist(description);
            return description;
        }
        return entityManager.merge(description);
    }
}
