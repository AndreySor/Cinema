package edu.school21.cinema.service.Impl;

import edu.school21.cinema.model.Message;
import edu.school21.cinema.repository.MessageRepository;
import edu.school21.cinema.service.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    public MessageServiceImpl(@Qualifier("messageRepositoryImpl") MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getLastTwelveMessagesFromFilmId(Long filmId) {
        return messageRepository.getLastTwelveMessagesFromFilmId(filmId);
    }

    @Override
    public void save(Message message) {
        message.setDate(new Date());
        messageRepository.save(message);
    }
}
