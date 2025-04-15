package com.khalid.ecommerce_system.service;

import com.khalid.ecommerce_system.exception.FileUploadException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class UploadImageInputParameter{
        private MultipartFile file;
        private String category;
        private String fileName;
    }
    String uploadImage(UploadImageInputParameter uploadImageInputParameter) throws IOException, FileUploadException;
}
