package edu.school21.cinema.repository;

import edu.school21.cinema.model.Film;

public interface FilmRepository extends BaseCRUDRepository<Film> {

    Film getByTitle(String title);
}
