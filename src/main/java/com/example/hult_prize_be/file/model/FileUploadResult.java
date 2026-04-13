package com.example.hult_prize_be.file.model;

public record FileUploadResult(
        String bucket,
        String objectName,
        String fileUrl,
        String originalFilename,
        String contentType,
        long size
) {
}
