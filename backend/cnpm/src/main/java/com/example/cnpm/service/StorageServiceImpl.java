package com.example.cnpm.service;

import com.example.cnpm.config.AcceptedFileType;
import com.example.cnpm.entity.Confession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.CharacterPredicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

import org.apache.commons.text.RandomStringGenerator;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Value("${location.image}")
    private String imageFolder;

    public String saveImageFile(MultipartFile file, Long id) {
        try {
            if (file == null || !AcceptedFileType.image.contains(file.getContentType())) {
                return null;
            }
            var is = file.getInputStream();

            // Check if the directory exists, if not, create it
            Path directoryPath = Paths.get(imageFolder);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            StringBuilder pathToSave = new StringBuilder(imageFolder + id);
            pathToSave.append(".").append(FilenameUtils.getExtension(file.getOriginalFilename()));
            pathToSave = new StringBuilder(pathToSave.toString().replaceAll("[\\\\:\\*\\?\\\"<>\\|]", ""));
            Files.copy(is, Paths.get(pathToSave.toString()), StandardCopyOption.REPLACE_EXISTING);
            return pathToSave.toString();
        } catch (Exception e) {
            log.error("Error: ", e);
            return null;
        }
    }

    @Override
    public byte[] getImageFile(Confession confession) {
        String imageFileType = confession.getImage().substring(confession.getImage().lastIndexOf(".") + 1);
        String path = imageFolder + confession.getId() + "." + imageFileType;
        try {
            return Files.readAllBytes(new File(path).toPath());
        } catch (Exception e) {
            log.error("Error: ", e);
            return null;
        }
    }

    @Override
    public void deleteFile(String fileLocation) {
        try {
            Files.deleteIfExists(Paths.get(fileLocation));
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
}
