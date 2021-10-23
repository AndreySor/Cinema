package edu.school21.cinema.service;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.model.SaveFilm;

import java.util.List;

public interface FilmService {

    void saveFilm(SaveFilm saveFilm);
    void updateFilm(SaveFilm saveFilm);
    List<Film> getAll();
}
