package edu.school21.cinema.servlet;

import edu.school21.cinema.model.Hall;
import edu.school21.cinema.repository.HallRepository;
import edu.school21.cinema.repository.impl.HallRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HallController {

    private final HallRepository hallRepository;

    @Autowired
    public HallController(HallRepositoryImpl hallRepository) {
        this.hallRepository = hallRepository;

    }

    @RequestMapping("/admin/panel/halls")
    public String showAllHals(Model model) {
        List<Hall> halls = hallRepository.getAll();
        model.addAttribute("halls", halls);
        return "halls";
    }
}
