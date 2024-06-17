package br.com.portfolio.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

public interface StorageService {
    void createBucket(String bucketName);
    URI uploadFile(String projectName, MultipartFile file);
    List<URI> getFiles(String projectName);
    URI updateFile(String projectName, String nameFile, MultipartFile newFile);
    void deleteFile(String projectName, String nameFile);
}
