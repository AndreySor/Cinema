package edu.school21.cinema.repository;

import edu.school21.cinema.model.Hall;

import java.util.List;

public interface BaseCRUDRepository<T> {
    List<T> getAll();
    T get(Long id);
    void save(T entity);
}
