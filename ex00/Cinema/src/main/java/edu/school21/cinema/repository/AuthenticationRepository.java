package edu.school21.cinema.repository;

import edu.school21.cinema.model.Authentication;

import java.util.List;

public interface AuthenticationRepository {

    List<Authentication> getAll();
    Authentication get(Long id);
    Authentication save(Authentication authentication);
    Long getCountRows();
}
