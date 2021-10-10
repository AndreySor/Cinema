package edu.school21.cinema.repository.impl;

import edu.school21.cinema.model.Message;
import edu.school21.cinema.repository.MessageRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Message> getAll() {
        return entityManager.createQuery("FROM Message", Message.class).getResultList();
    }

    @Override
    @Transactional
    public List<Message> getLastTwelveMessagesFromFilmId(Long filmId) {
       return entityManager.createQuery("FROM Message WHERE film.filmId =:filmId ORDER BY film.filmId DESC", Message.class).setParameter("filmId", filmId).setMaxResults(20).getResultList();
    }

    @Override
    @Transactional
    public Message get(Long id) {
        return entityManager.find(Message.class, id);
    }

    @Override
    @Transactional
    public void save(Message message) {
        entityManager.merge(message);
    }
}
