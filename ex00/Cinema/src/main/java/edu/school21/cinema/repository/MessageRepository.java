package edu.school21.cinema.repository;

import edu.school21.cinema.model.Message;

import java.util.List;

public interface MessageRepository extends BaseCRUDRepository<Message>{

    List<Message> getLastTwelveMessagesFromFilmId(Long filmId);
}
