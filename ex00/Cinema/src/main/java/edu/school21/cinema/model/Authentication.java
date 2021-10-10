package edu.school21.cinema.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cinema_authentications")
public class Authentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "login")
    String login;

    @Column(name = "authentication_date")
    private Date date;

    @Column(name = "ip_address")
    String ipAddress;

    public Authentication() {
    }

    public Authentication(Long id, String login, Date date, String ipAddress) {
        this.id = id;
        this.login = login;
        this.date = date;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
