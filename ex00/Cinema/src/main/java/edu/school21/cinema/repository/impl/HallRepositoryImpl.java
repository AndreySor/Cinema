package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Hall;
import edu.school21.cinema.repository.BaseCRUDRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HallRepositoryImpl implements BaseCRUDRepository<Hall> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Hall> getAll() {
        return entityManager.createQuery("from Hall ", Hall.class).getResultList();
    }

    @Override
    @Transactional
    public Hall get(Long id) {
        return entityManager.find(Hall.class, id);
    }

    @Override
    @Transactional
    public void save(Hall hall) {
        entityManager.merge(hall);
    }
}
