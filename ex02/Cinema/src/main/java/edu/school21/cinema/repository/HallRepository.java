package edu.school21.cinema.repository;

import edu.school21.cinema.model.Hall;

public interface HallRepository extends BaseCRUDRepository<Hall> {

    Hall getFromSerialNumber(Integer serialNumber);
}
