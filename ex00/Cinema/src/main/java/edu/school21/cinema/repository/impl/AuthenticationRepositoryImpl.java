package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Authentication;
import edu.school21.cinema.repository.AuthenticationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Authentication save(Authentication authentication) {
        entityManager.persist(authentication);
        return authentication;
    }

    @Override
    public Long getCountRows() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Authentication.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    @Transactional
    public List<Authentication> getAllByUserId(Long id) {
        return entityManager.createQuery("Select new Authentication(a.id, a.user, a.date, a.ipAddress) FROM Authentication a WHERE a.user.id = :id ORDER BY a.id DESC", Authentication.class).
                setParameter("id", id).
                getResultList();
    }
}
