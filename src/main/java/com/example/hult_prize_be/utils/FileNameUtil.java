package com.example.hult_prize_be.utils;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

public final class FileNameUtil {
    private FileNameUtil() {
    }

    public static String buildObjectName(String directory, String originalFilename) {
        String cleanDirectory = StringUtils.hasText(directory)
                ? directory.replace("\\", "/").replaceAll("^/|/$", "")
                : "";
        String cleanFilename = StringUtils.cleanPath(Objects.requireNonNullElse(originalFilename, "file"));
        String extension = extractExtension(cleanFilename);
        String storedFilename = UUID.randomUUID() + extension;

        if (!StringUtils.hasText(cleanDirectory)) {
            return storedFilename;
        }
        return cleanDirectory + "/" + storedFilename;
    }

    public static String extractExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index < 0) {
            return "";
        }
        return filename.substring(index);
    }
}
