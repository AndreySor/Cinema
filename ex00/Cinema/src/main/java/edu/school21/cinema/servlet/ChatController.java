package edu.school21.cinema.servlet;

import edu.school21.cinema.model.Authentication;
import edu.school21.cinema.model.Film;
import edu.school21.cinema.model.Message;
import edu.school21.cinema.model.response.BaseResponse;
import edu.school21.cinema.repository.FilmRepository;
import edu.school21.cinema.service.AuthenticationService;
import edu.school21.cinema.service.MessageService;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {

    private final AuthenticationService authenticationService;
    private final MessageService messageService;
    private final FilmRepository filmRepository;

    public ChatController(AuthenticationService authenticationService,
                          MessageService messageService,
                          FilmRepository filmRepository) {
        this.authenticationService = authenticationService;
        this.messageService = messageService;
        this.filmRepository = filmRepository;
    }

    @GetMapping(value = "/films/{film-id}/chat")
    public String startChat(@PathVariable("film-id") Long filmId, Model model) {
        Film film = filmRepository.get(filmId);
        List<Message> lastMessages = messageService.getLastTwelveMessagesFromFilmId(filmId);
        model.addAttribute("film", film);
        model.addAttribute("history", lastMessages);
        return "chat";
    }

    @GetMapping(value = "/userInfo/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public @ResponseBody List<Authentication> getAuthList(@ModelAttribute("id") Long id) {
        List<Authentication> rs = authenticationService.getAllByUserId(id);
        if (rs.isEmpty()) {
            return null;
        }
        return rs;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        if (message.getType().equals(Message.MessageType.CHAT)) {
            messageService.save(message);
        }
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getUser().getLogin());
        String clientIp = (String) headerAccessor.getSessionAttributes().get("ip");
        message.getUser().setId(authenticationService.authUser(message.getUser(), clientIp));
        return message;
    }
}
