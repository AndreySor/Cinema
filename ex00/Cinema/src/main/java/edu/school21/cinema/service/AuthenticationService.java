package edu.school21.cinema.service;

import edu.school21.cinema.model.Authentication;
import edu.school21.cinema.model.User;

import java.util.List;

public interface AuthenticationService {

    Long authUser(User user, String ip);
    List<Authentication> getAllByUserId(Long id);
}
