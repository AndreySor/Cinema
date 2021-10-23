package edu.school21.cinema.model;

import javax.persistence.*;

@Entity
@Table(name = "cinema_films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "age_restrictions")
    private Integer ageRestriction;

    @Column(name = "description")
    private String description;

    @Column(name = "poster")
    private String poster;

    public Film() {
    }

    public Film(String title, Integer releaseYear, Integer ageRestriction, String description, String poster) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.ageRestriction = ageRestriction;
        this.description = description;
        this.poster = poster;
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

    public Film withId(Long id) {
        this.filmId = id;
        return this;
    }
}
