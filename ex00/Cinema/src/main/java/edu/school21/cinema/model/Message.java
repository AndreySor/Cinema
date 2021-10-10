package edu.school21.cinema.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cinema_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    String message;

    @ManyToOne
    @JoinColumn(name = "login")
    Authentication authentication;

    @Column(name = "message_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public Message() {
    }

    public Message(Long id, String message, Authentication authentication, Date date, Film film) {
        this.id = id;
        this.message = message;
        this.authentication = authentication;
        this.date = date;
        this.film = film;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
