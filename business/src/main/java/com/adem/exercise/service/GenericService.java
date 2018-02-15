package com.adem.exercise.service;

import java.util.List;

public interface GenericService<T> {
    List<T> listAll();

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}
