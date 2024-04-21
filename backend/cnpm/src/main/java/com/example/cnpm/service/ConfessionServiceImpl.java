package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.CommentDto;
import com.example.cnpm.controller.dtos.CommentRequest;
import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Comment;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.entity.User;
import com.example.cnpm.repository.CommentRepository;
import com.example.cnpm.repository.ConfessionRepository;
import com.example.cnpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfessionServiceImpl implements ConfessionService {

    @Autowired
    ConfessionRepository confessionRepository;
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    StorageService storageService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Confession addConfession(ConfessionDto confessionDto, boolean isApproved) {
        Confession confession = new Confession();

        confession.setName(confessionDto.getName());
        confession.setDescription(confessionDto.getDescription());
        confession.setContent(confessionDto.getContent());
        confession.setAuthor(confessionDto.getAuthor());
        confession.setIsApproved(isApproved);
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
        confession.setIsApproved(confessionDto.getIsApproved());

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

    @Override
    public Page<CommentDto> getComments(Long id, int page) {
        List<CommentDto> commentDtos = commentRepository.findAll().stream().map(CommentDto::new).collect(Collectors.toList());
        int pageSize = 10;
        int totalElements = commentDtos.size();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalElements);
        return new PageImpl<>(commentDtos.subList(start, end), PageRequest.of(page - 1, pageSize), totalElements);
    }

    @Override
    public Confession acceptConfession(Long id) {
        Confession confession = confessionRepository.findById(id).orElse(null);
        if (confession == null) {
            return null;
        }
        confession.setIsApproved(true);
        return confessionRepository.save(confession);
    }

    @Override
    public byte[] getConfessionImage(Long id) {
        Confession confession = confessionRepository.findById(id).orElse(null);
        if (confession == null) {
            return null;
        }
        return storageService.getImageFile(confession);
    }

    @Override
    public Page<ConfessionDto> getAllConfession(int page) {
        List<ConfessionDto> confessionDtos = confessionRepository.findAllApprovedConfession().stream().map(ConfessionDto::new).collect(Collectors.toList());
        int pageSize = 10;
        int totalElements = confessionDtos.size();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalElements);
        return new PageImpl<>(confessionDtos.subList(start, end), PageRequest.of(page - 1, pageSize), totalElements);
    }
}
