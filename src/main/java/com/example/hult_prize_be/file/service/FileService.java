package com.example.hult_prize_be.file.service;

import com.example.hult_prize_be.file.model.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileUploadResult upload(MultipartFile file, String directory);
}
