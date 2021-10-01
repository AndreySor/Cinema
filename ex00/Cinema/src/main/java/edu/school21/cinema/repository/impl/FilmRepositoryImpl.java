package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.repository.BaseCRUDRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FilmRepositoryImpl implements BaseCRUDRepository<Film> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Film> getAll() {
        return entityManager.createQuery("FROM Film", Film.class).getResultList();
    }

    @Override
    @Transactional
    public Film get(Long id) {
        return entityManager.find(Film.class, id);
    }

    @Override
    @Transactional
    public void save(Film film) {
        entityManager.merge(film);
    }
}
