package com.example.hult_prize_be.file.service;

import com.example.hult_prize_be.file.model.FileDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileDto.UploadResponse upload(MultipartFile file, String directory);
}
