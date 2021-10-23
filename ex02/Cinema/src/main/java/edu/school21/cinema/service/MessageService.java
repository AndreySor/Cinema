package edu.school21.cinema.service;

import edu.school21.cinema.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getLastTwelveMessagesFromFilmId(Long filmId);
    void save(Message message);
}
