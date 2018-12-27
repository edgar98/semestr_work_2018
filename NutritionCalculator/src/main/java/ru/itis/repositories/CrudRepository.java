package ru.itis.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll(Long id);
    T find(Long id);
    void save(T model);
    void delete(Long id);
    void update(T model);
}
