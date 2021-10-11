package edu.school21.cinema.servlet;

import edu.school21.cinema.model.Film;
import edu.school21.cinema.model.Message;
import edu.school21.cinema.repository.AuthenticationRepository;
import edu.school21.cinema.repository.FilmRepository;
import edu.school21.cinema.repository.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final AuthenticationRepository authenticationRepository;
    private final MessageRepository messageRepository;
    private final FilmRepository filmRepository;

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          AuthenticationRepository authenticationRepository,
                          MessageRepository messageRepository,
                          FilmRepository filmRepository) {
        this.messagingTemplate = messagingTemplate;
        this.authenticationRepository = authenticationRepository;
        this.messageRepository = messageRepository;
        this.filmRepository = filmRepository;
    }

    //Подумать над проверкое если не лонг
    @RequestMapping(value = "/films/{filmId}/chat", method = RequestMethod.GET)
    public String showAllFilm(@PathVariable String filmId, Model model, HttpServletRequest request, HttpServletResponse response) {
        String login;
        Cookie userName = WebUtils.getCookie(request, "userName");
        if (userName == null) {
            Long id = authenticationRepository.getCountRows();
            login = "User" + (id + 1);
            response.addCookie(new Cookie("userName", login));
        } else {
            login = userName.getValue();
        }
        Film film = filmRepository.get(Long.valueOf(filmId));
        if (film == null) {
            model.addAttribute("errorMessage", "Фильм не найден");
        } else {
            model.addAttribute("filmId", filmId);
            model.addAttribute("userName", login);
            model.addAttribute("title", film.getTitle());
        }
        return "chat";
    }

    @MessageMapping("/auth")
    public void processAuth(@Payload Message message) {
        List<Message> messages = messageRepository.getLastTwelveMessagesFromFilmId(message.getFilm().getFilmId());

        for (Message message1: messages) {
            messagingTemplate.convertAndSendToUser(message.getFilm().getFilmId().toString(), "/chat", message1);
        }
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        messageRepository.save(message);
        messagingTemplate.convertAndSendToUser(message.getFilm().getFilmId().toString(), "/chat", message.getMessage());
    }
}
