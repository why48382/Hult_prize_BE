package com.example.hult_prize_be.file.service;

import com.example.hult_prize_be.config.GcsStorageProperties;
import com.example.hult_prize_be.file.model.FileDto;
import com.example.hult_prize_be.utils.FileNameUtil;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;

@Service
@RequiredArgsConstructor
public class GcsFileService implements FileService {
    private final Storage storage;
    private final GcsStorageProperties gcsStorageProperties;

    @Override
    public FileDto.UploadResponse upload(MultipartFile file, String directory) {
        validate(file);

        String objectName = FileNameUtil.buildObjectName(directory, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(gcsStorageProperties.getBucket(), objectName)
                .setContentType(file.getContentType())
                .build();

        Blob blob;
        try {
            blob = storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException("GCS 파일 업로드에 실패했습니다.", e);
        }

        return new FileDto.UploadResponse(
                blob.getBucket(),
                blob.getName(),
                "https://storage.googleapis.com/" + blob.getBucket() + "/" + blob.getName(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize()
        );
    }

    private void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("업로드할 파일이 없습니다.");
        }
        if (!StringUtils.hasText(gcsStorageProperties.getBucket())) {
            throw new RuntimeException("GCS 버킷 설정이 비어 있습니다.");
        }
    }
}
