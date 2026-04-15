package com.example.hult_prize_be.file.model;

public class FileDto {
    public record UploadResponse(
            String bucket,
            String objectName,
            String fileUrl,
            String originalFilename,
            String contentType,
            long size
    ) {
    }
}
