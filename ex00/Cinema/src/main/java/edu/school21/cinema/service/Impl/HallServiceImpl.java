package edu.school21.cinema.service.Impl;

import edu.school21.cinema.model.Hall;
import edu.school21.cinema.repository.HallRepository;
import edu.school21.cinema.service.HallService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HallServiceImpl implements HallService {

    private HallRepository hallRepository;

    public HallServiceImpl(@Qualifier("hallRepositoryImpl") HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public List<Hall> getAll() {
        return hallRepository.getAll();
    }
}
