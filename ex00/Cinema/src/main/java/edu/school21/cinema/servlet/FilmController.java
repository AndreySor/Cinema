package edu.school21.cinema.servlet;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.List;

@Controller
public class FilmController {

    private final FilmRepository filmRepository;

    private String imagesPath;

    @Autowired
    public FilmController(@Qualifier("filmRepositoryImpl") FilmRepository filmRepository,
                          @Qualifier("pathToImagesFolder") String pathToImagesFolder) {
        this.filmRepository = filmRepository;
        this.imagesPath = pathToImagesFolder;
    }

    @RequestMapping(value = "/admin/panel/films", method = RequestMethod.GET)
    public String showAllFilm(Model model) {
        List<Film> films = filmRepository.getAll();
        model.addAttribute("films", films);

        return "films";
    }

    @RequestMapping(value = "/admin/panel/addNewFilm", method = RequestMethod.GET)
    public String addNewFilm(Model model) {
        Film film = new Film();
        model.addAttribute("film", film);

        return "addFilm";
    }
    @RequestMapping(value = "/admin/panel/saveNewFilm", method = RequestMethod.POST)
    public String saveNewFilm(Model model, @ModelAttribute("film") Film film) {
        if (film == null || film.getTitle() == null || film.getAgeRestriction() == null || film.getReleaseYear() == null) {
            model.addAttribute("errorMessage", "Please enter all data");
            return "addFilm";
        } else if (filmRepository.getByTitle(film.getTitle()) != null) {
            model.addAttribute("errorMessage", "A film with this title already exists");
            return "addFilm";
        } else {
            filmRepository.save(film);

        }
        return "redirect:/admin/panel/films";
    }
}
