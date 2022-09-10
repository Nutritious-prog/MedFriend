package com.nutritiousprog.medfriend.services;

import java.util.List;
/**
    This interface contains methods that need to be implemented in all my services
 */
public interface BasicService<T> {
    T create(T entity);

    boolean delete(Long id);

    T update(Long id, T entity);

    T getById(Long id);

    List<T> getAll();

    boolean checkIfEntityExistsInDb(T entity);
}
