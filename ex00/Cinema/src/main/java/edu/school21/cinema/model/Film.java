package edu.school21.cinema.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cinema_films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long filmId;

    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "age_restrictions")
    private Integer ageRestriction;

    @Column(name = "description")
    private String description;

    @Column(name = "poster")
    private String poster;

    @OneToMany(mappedBy = "film", fetch = FetchType.EAGER)
    private Set<Session> sessions = new HashSet<>();

    public Film() {
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", ageRestriction=" + ageRestriction +
                ", description='" + description + '\'' +
                ", poster='" + poster + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
