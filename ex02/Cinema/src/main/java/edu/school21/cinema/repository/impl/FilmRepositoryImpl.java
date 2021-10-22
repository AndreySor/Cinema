package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.repository.FilmRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FilmRepositoryImpl implements FilmRepository {

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

    @Override
    public Film getByTitle(String title) {
        Film film = null;
        try{
            film = entityManager.createQuery("FROM Film where title =:title", Film.class).setParameter("title", title).getSingleResult();
        } catch (NoResultException ignored){}
        return film;
    }
}
