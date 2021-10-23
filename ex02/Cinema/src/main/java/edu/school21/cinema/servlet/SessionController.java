package edu.school21.cinema.servlet;

import edu.school21.cinema.mapper.SessionsResponseMapper;
import edu.school21.cinema.model.Session;
import edu.school21.cinema.model.response.BaseResponse;
import edu.school21.cinema.service.SessionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/sessions")
public class SessionController {

    private SessionService sessionService;
    private SessionsResponseMapper mapper;

    public SessionController(SessionService sessionService, SessionsResponseMapper mapper) {
        this.sessionService = sessionService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/")
    public String getSearchField() {
        return "sessionsSearching";
    }

    @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    BaseResponse searching(@ModelAttribute("filmName") String request) {
        if (request.isEmpty()) {
            return null;
        }
        List<Session> serviceResponse = sessionService.searchByRequest(request);
        return new BaseResponse(mapper.mapToResponse(serviceResponse));
    }

    @GetMapping(value = "/{session-id}")
    public String getFilmInfoBySessionId(@PathVariable("session-id") Long sessionId, Model model) {
        Session session = sessionService.get(sessionId);
        mapper.encodePoster(session.getFilm());
        model.addAttribute("info", session);
        return "filmSessionInfo";
    }
}
