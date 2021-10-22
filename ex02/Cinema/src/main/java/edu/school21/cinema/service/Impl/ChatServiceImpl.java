package edu.school21.cinema.service.Impl;

import edu.school21.cinema.service.ChatService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private String imagesPath;

    @Autowired
    public ChatServiceImpl(@Qualifier("pathToImagesFolder") String pathToImagesFolder) {
        this.imagesPath = pathToImagesFolder;
    }

    @Override
    public void saveAvatar(MultipartFile file, Long userId) throws IOException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = date + file.getOriginalFilename();

        String filePath = imagesPath + userId + File.separator + fileName;

        if (!Files.exists(Paths.get(imagesPath + userId))) {
            if (!Files.exists(Paths.get(imagesPath))) {
                Files.createDirectories(Paths.get(imagesPath));
            }
            Files.createDirectories(Paths.get(imagesPath + userId));
        }
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public List<String> getListOfAvatarByUserId(Long id) {
        List<String> listOfAvatar = new ArrayList<>();
        File imageDir = new File(imagesPath + id);
        if (!imageDir.exists()) {
            return null;
        }

        for (File avatar : imageDir.listFiles()) {
            listOfAvatar.add(avatar.getName());
        }
        return listOfAvatar;
    }

    @Override
    public String getAvatar(String fileName, Long userId) {
        byte[] file = new byte[0];
        try {
            file = FileUtils.readFileToByteArray(new File(imagesPath + userId + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encode = Base64.getEncoder().encodeToString(file);

        return encode;
    }
}
