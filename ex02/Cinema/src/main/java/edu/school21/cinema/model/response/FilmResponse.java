package edu.school21.cinema.model.response;

//ex01
public class FilmResponse {

    private String name;

    private String posterUrl;

    public FilmResponse(String name, String posterUrl) {
        this.name = name;
        this.posterUrl = posterUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
