package edu.school21.cinema.repository;

import edu.school21.cinema.model.User;

public interface UserRepository {

    Long save(User user);
    User findByLogin(String login);
}
