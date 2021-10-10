package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Authentication;
import edu.school21.cinema.repository.AuthenticationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Authentication> getAll() {
        return entityManager.createQuery("FROM Authentication", Authentication.class).getResultList();
    }

    @Override
    public Authentication get(Long id) {
        return entityManager.find(Authentication.class, id);
    }

    @Override
    public Authentication save(Authentication authentication) {
        return entityManager.merge(authentication);
    }

    @Override
    public Long getCountRows() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Authentication.class)));
        return entityManager.createQuery(query).getSingleResult();
    }
}
