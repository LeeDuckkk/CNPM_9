package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Confession;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
public class ConfessionDto {

    private Long id;

    private MultipartFile image;

    private String name;
    private String description;
    private String content;
    private String author;

    private Date createdAt;
    private Boolean isApproved;

    public ConfessionDto(Confession confession) {
        this.id = confession.getId();
        this.name = confession.getName();
        this.description = confession.getDescription();
        this.content = confession.getContent();
        this.author = confession.getAuthor();
        this.createdAt = confession.getCreatedAt();
        this.isApproved = confession.getIsApproved();
    }
}
