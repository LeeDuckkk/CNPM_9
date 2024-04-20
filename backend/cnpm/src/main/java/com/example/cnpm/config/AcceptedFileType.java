package com.example.cnpm.config;

import java.util.List;

public class AcceptedFileType {
    public static List<String> image = List.of(
            "image/apng", "image/avif", "image/gif", "image/jpeg", "image/png", "image/svg+xml", "image/webp");
    public static List<String> docx = List.of("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
}
