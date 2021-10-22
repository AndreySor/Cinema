package edu.school21.cinema.service.Impl;

import edu.school21.cinema.exception.NotAllDataException;
import edu.school21.cinema.exception.ObjectAlreadyExistsException;
import edu.school21.cinema.model.Film;
import edu.school21.cinema.model.SaveFilm;
import edu.school21.cinema.repository.FilmRepository;
import edu.school21.cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;
    private String imagesPath;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public FilmServiceImpl(@Qualifier("filmRepositoryImpl") FilmRepository filmRepository,
                           @Qualifier("pathToImagesFolder") String pathToImagesFolder,
                           SimpMessagingTemplate messagingTemplate) {
        this.filmRepository = filmRepository;
        this.imagesPath = pathToImagesFolder;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void saveFilm(SaveFilm saveFilm) {
        if (saveFilm == null || saveFilm.getTitle() == null || saveFilm.getAgeRestriction() == null || saveFilm.getReleaseYear() == null) {
            throw new NotAllDataException("Please enter all data");
        } else if (filmRepository.getByTitle(saveFilm.getTitle()) != null) {
            throw new ObjectAlreadyExistsException("A film with this title already exists");
        } else {
            String key = "";
            if (saveFilm.getFile().getOriginalFilename() != null && !saveFilm.getFile().getOriginalFilename().isEmpty()) {
                try {
                    key = UUID.randomUUID().toString();
                    upload(saveFilm.getFile().getBytes(), key, saveFilm.getTitle());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Film film = new Film(saveFilm.getTitle(), saveFilm.getReleaseYear(), saveFilm.getAgeRestriction(), saveFilm.getDescription(), key);
            filmRepository.save(film);
        }
    }

    @Override
    public void updateFilm(SaveFilm saveFilm) {
        String key;
        if (saveFilm.getFile().getOriginalFilename() != null && !saveFilm.getFile().getOriginalFilename().isEmpty()) {
            try {
                Film film = filmRepository.getByTitle(saveFilm.getTitle());
                key = UUID.randomUUID().toString();
                upload(saveFilm.getFile().getBytes(), key, saveFilm.getTitle());
                film.setPoster(key);
                filmRepository.save(film);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upload(byte[] resource, String keyName, String title) throws IOException {
        if (!Files.exists(Paths.get(imagesPath + title))) {
            if (!Files.exists(Paths.get(imagesPath))) {
                Files.createDirectories(Paths.get(imagesPath));
            }
            Files.createDirectory(Paths.get(imagesPath + title));
        }

        Path path = Paths.get(imagesPath + title, keyName);
        Path file = Files.createFile(path);
        try (FileOutputStream stream = new FileOutputStream(file.toString())) {
            stream.write(resource);
        }
    }

    public List<Film> getAll() {
        return filmRepository.getAll();
    }
}
