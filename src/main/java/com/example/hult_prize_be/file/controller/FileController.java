package com.example.hult_prize_be.file.controller;

import com.example.hult_prize_be.file.model.FileDto;
import com.example.hult_prize_be.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileDto.UploadResponse> upload(@RequestPart("file") MultipartFile file, @RequestParam(defaultValue = "test") String directory) {
        return ResponseEntity.ok(fileService.upload(file, directory));
    }
}
