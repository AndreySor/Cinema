package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Hall;
import edu.school21.cinema.repository.HallRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class HallRepositoryImpl implements HallRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Hall> getAll() {
        return entityManager.createQuery("from Hall ", Hall.class).getResultList();
    }
}
