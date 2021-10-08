package edu.school21.cinema.servlet;

import edu.school21.cinema.mapper.SessionsResponseMapper;
import edu.school21.cinema.model.Session;
import edu.school21.cinema.model.response.BaseResponse;
import edu.school21.cinema.service.SessionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//функционал для ex01

@Controller
@RequestMapping(value = "/sessions")
public class SessionController {

    private SessionService sessionService;
    private SessionsResponseMapper mapper;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
        this.mapper = new SessionsResponseMapper();
    }

    @GetMapping(value = "/test")
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
}
