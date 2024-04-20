package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Confession;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConfessionDto {

    private MultipartFile image;

    private String name;
    private String description;
    private String content;
    private String author;
    private Boolean isApproved;

    public ConfessionDto() {
    }

    public ConfessionDto(Confession confession) {
        this.name = confession.getName();
        this.description = confession.getDescription();
        this.content = confession.getContent();
        this.author = confession.getAuthor();
        this.isApproved = confession.getIsApproved();
    }
}
