package com.example.cnpm.controller.dtos;

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
}
