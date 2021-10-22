package edu.school21.cinema.repository;

import edu.school21.cinema.model.Session;

import java.util.List;

public interface SessionRepository extends BaseCRUDRepository<Session> {

    List<Session> searchByRequest(String request);
}
