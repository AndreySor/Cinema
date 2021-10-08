package edu.school21.cinema.mapper;

import edu.school21.cinema.model.Session;
import edu.school21.cinema.model.response.FilmResponse;
import edu.school21.cinema.model.response.SessionsResponse;

import java.util.ArrayList;
import java.util.List;

//ex01
public class SessionsResponseMapper {

    public List<SessionsResponse> mapToResponse(List<Session> rs) {
        List<SessionsResponse> response = new ArrayList<>();

        if (rs.isEmpty()) {
            return response;
        }
        for (Session s : rs) {
            SessionsResponse sessionsResponse = new SessionsResponse();
            sessionsResponse.setId(s.getId());
            sessionsResponse.setDateTime(s.getDate());

            FilmResponse filmResponse = new FilmResponse(s.getFilm().getTitle());
            sessionsResponse.setFilm(filmResponse);

            response.add(sessionsResponse);
        }
        return response;
    }
}
