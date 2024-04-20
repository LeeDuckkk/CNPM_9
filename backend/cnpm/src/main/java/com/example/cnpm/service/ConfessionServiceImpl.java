package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.repository.ConfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConfessionServiceImpl implements ConfessionService {

    @Autowired
    ConfessionRepository confessionRepository;

    @Autowired
    StorageService storageService;

    @Override
    public Confession addConfession(ConfessionDto confessionDto) {
        Confession confession = new Confession();

        confession.setName(confessionDto.getName());
        confession.setDescription(confessionDto.getDescription());
        confession.setContent(confessionDto.getContent());
        confession.setAuthor(confessionDto.getAuthor());
        confession = confessionRepository.save(confession);

        String path = storageService.saveImageFile(confessionDto.getImage(), confession.getId());
        if (path != null) {
            confession.setImage(path);
        }
        return confessionRepository.save(confession);
    }

    @Override
    public Confession updateConfession(Long id, ConfessionDto confessionDto) {
        Confession confession = confessionRepository.findById(id).orElse(null);
        MultipartFile imageFile = confessionDto.getImage();
        if (confession == null) {
            return null;
        }
        confession.setName(confessionDto.getName());
        confession.setDescription(confessionDto.getDescription());
        confession.setContent(confessionDto.getContent());
        confession.setAuthor(confessionDto.getAuthor());

        if (imageFile != null && !imageFile.isEmpty()) {
            storageService.deleteFile(confession.getImage());
            String screenshotFileLocation = storageService.saveImageFile(imageFile, confession.getId());
            if (screenshotFileLocation != null) {
                confession.setImage(screenshotFileLocation);
            }
        }
        return confessionRepository.save(confession);
    }

    @Override
    public void deleteConfession(Long id) {
        Confession confession = confessionRepository.findById(id).orElse(null);
        if (confession == null) {
            return;
        }
        storageService.deleteFile(confession.getImage());
        confessionRepository.delete(confession);
    }

    @Override
    public Confession getConfession(Long id) {
        return confessionRepository.findById(id).orElse(null);
    }
}
