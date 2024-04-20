package com.example.cnpm.service;

import com.example.cnpm.entity.Confession;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String saveImageFile(MultipartFile file, Long id);

    byte[] getImageFile(Confession confession);

    void deleteFile(String fileLocation);
}
