package edu.school21.cinema.servlet;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.model.Hall;
import edu.school21.cinema.model.Session;
import edu.school21.cinema.service.FilmService;
import edu.school21.cinema.service.HallService;
import edu.school21.cinema.service.SessionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin/panel")
public class SessionController {

    private final SessionService sessionService;
    private final FilmService filmService;
    private final HallService hallService;
    private final SimpleDateFormat formatter;

    public SessionController(SessionService sessionService, FilmService filmService, HallService hallService) {
        this.sessionService = sessionService;
        this.filmService = filmService;
        this.hallService = hallService;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    }

    @GetMapping(value = "/sessions", consumes = MediaType.ALL_VALUE)
    public String getSessionList(Model model) {
        model.addAttribute("sessions", sessionService.getAll());
        model.addAttribute("halls", hallService.getAll());
        model.addAttribute("films", filmService.getAll());

        return "sessions";
    }

    @PostMapping(value = "/addSession", consumes = MediaType.ALL_VALUE)
    public String addSession(@ModelAttribute("selectedFilm")Long filmId,
                             @ModelAttribute("selectedHall")Long hallId,
                             @ModelAttribute("ticketCost")Integer ticketCost,
                             @ModelAttribute("sessionDate")String date) throws ParseException {
        Session session = new Session();
        session.setHall(new Hall().withId(hallId));
        session.setFilm(new Film().withId(filmId));
        session.setTicketCost(ticketCost);
        session.setDate(formatter.parse(date));
        sessionService.save(session);

        return "redirect:/admin/panel/sessions";
    }
}
