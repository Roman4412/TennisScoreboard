package com.pustovalov.dao;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E, I> {
    E save(E entity);

    Optional<E> find(I id);

    void update(E entity);

    void delete(I id);

    List<E> findAll();

}
