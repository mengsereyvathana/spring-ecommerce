package com.spring.ecommerce.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUploadUtil {

    private static final String FILE_EXTENSIONS = "jpg, png, jpeg, gif";

    public static String saveFile(String uploadDir, MultipartFile file, boolean isRandomName) throws IOException {
        String fileName = generateFileName(uploadDir, file, isRandomName);
        Files.write(Paths.get(uploadDir, fileName), file.getBytes());
        return fileName;
    }

    public static List<String> saveAllFiles(String uploadDir, MultipartFile[] files, boolean isRandomName) throws IOException {
        if (files == null) {
            throw new IllegalArgumentException("No files provided");
        }

        List<String> savedFileNames = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            try {
                String fileName = generateFileName(uploadDir, file, isRandomName);
                Files.write(Paths.get(uploadDir, fileName), file.getBytes());
                savedFileNames.add(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return savedFileNames;
    }

    public static void deleteFile(String uploadDir, String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(uploadDir).resolve(fileName));
    }

    private static String generateFileName(String uploadDir, MultipartFile file, boolean isRandomName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = FilenameUtils.getExtension(fileName);

        if (!FILE_EXTENSIONS.contains(fileExtension)) {
            throw new IOException("Invalid file extension");
        }

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (isRandomName) {
            fileName = generateRandomFileName(fileName, fileExtension);
        }

        return fileName;
    }

    private static String generateRandomFileName(String name, String extension) {
        return FilenameUtils.removeExtension(name) + "_" +
                Calendar.getInstance().getTimeInMillis() + "_" +
                RandomStringUtil.random(8) + "." +
                extension;
    }
}
