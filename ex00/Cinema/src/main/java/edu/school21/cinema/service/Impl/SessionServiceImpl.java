package edu.school21.cinema.service.Impl;

import edu.school21.cinema.model.Session;
import edu.school21.cinema.repository.BaseCRUDRepository;
import edu.school21.cinema.service.SessionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionServiceImpl implements SessionService {

    private BaseCRUDRepository<Session> sessionRepository;

    public SessionServiceImpl(@Qualifier("sessionRepositoryImpl") BaseCRUDRepository<Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void save(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.getAll();
    }
}
