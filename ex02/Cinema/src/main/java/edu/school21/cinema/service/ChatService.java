package edu.school21.cinema.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChatService {

    void saveAvatar(MultipartFile file, Long userId) throws IOException;
    List<String> getListOfAvatarByUserId(Long id);
    String getAvatar(String fileName, Long userId);
}
