package edu.school21.cinema.model.response;

import java.util.List;

//ex01
public class BaseResponse {

    private List<SessionsResponse> sessions;

    public BaseResponse() {
    }

    public BaseResponse(List<SessionsResponse> sessions) {
        this.sessions = sessions;
    }

    public List<SessionsResponse> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionsResponse> sessions) {
        this.sessions = sessions;
    }
}
