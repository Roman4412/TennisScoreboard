package com.pustovalov.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<E, I> {
    E save(E entity);

    Optional<E> findById(I id);
    Optional<E> findByName(String name);

    void update(E entity);

    void delete(I id);

    List<E> findAll();

}
