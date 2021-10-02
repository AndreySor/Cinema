package edu.school21.cinema.repository;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.model.Hall;
import edu.school21.cinema.repository.BaseCRUDRepository;

public interface FilmRepository extends BaseCRUDRepository<Film> {

    Film getByTitle(String title);
}
