package edu.school21.cinema.service;

import edu.school21.cinema.model.Session;

import java.util.List;

public interface SessionService {

    void save(Session session);
    List<Session> getAll();
    Session get(Long id);

    //функционал для ex01
    List<Session> searchByRequest(String request);
}
