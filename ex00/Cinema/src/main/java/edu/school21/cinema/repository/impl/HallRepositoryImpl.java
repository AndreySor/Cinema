package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Hall;
import edu.school21.cinema.repository.HallRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HallRepositoryImpl implements HallRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Hall> getAll() {
        return entityManager.createQuery("FROM Hall ", Hall.class).getResultList();
    }

    @Override
    @Transactional
    public Hall get(Long id) {
        return entityManager.find(Hall.class, id);
    }

    @Override
    @Transactional
    public void save(Hall hall) {
        entityManager.persist(hall);
    }

    @Override
    @Transactional
    public Hall getFromSerialNumber(Integer serialNumber) {
        Hall hall = null;
        try{
            hall = entityManager.createQuery("FROM Hall where serialNumber =:serialNumber", Hall.class).setParameter("serialNumber", serialNumber).getSingleResult();
        } catch (NoResultException ignored){}
        return hall;
    }
}
