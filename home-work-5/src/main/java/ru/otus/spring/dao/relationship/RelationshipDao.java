package ru.otus.spring.dao.relationship;

import ru.otus.spring.model.entity.Entity;
import ru.otus.spring.model.relationship.Relationship;

import java.util.List;

public interface RelationshipDao<H extends Entity, D extends Entity, R extends Relationship<H, D>> {
    R getById(long id);
    List<R> getAllByHolder(H holder);
    List<R> updateAllByHolder(H holder);
    void delete(R relationship);
    void deleteAllByHolder(H holder);
    void deleteAllByDependent(D dependent);
    R save(R relationship);
    List<R> saveAllByHolder(H holder);
}
