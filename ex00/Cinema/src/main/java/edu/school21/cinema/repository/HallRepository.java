package edu.school21.cinema.repository;

import edu.school21.cinema.model.Hall;

import java.util.List;

public interface HallRepository {
    List<Hall> getAll();

    Hall getHall(Integer serialNumber);

    void save(Hall hall);
}
