package br.com.portfolio.service.impl;

import br.com.portfolio.exception.MinioException;
import br.com.portfolio.service.StorageService;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    @Value("${minio.url.getObject}")
    private String urlObject;

    @Value("${minio.bucket.name}")
    private String bucketName;

    private final MinioClient client;

    @Override
    public void createBucket(String bucketName) {
        try {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String uploadFile(String projectName, MultipartFile file) {
        try{
            var fileName = StringUtils.cleanPath(file.getOriginalFilename());
            var path = String.join(projectName,"/",fileName);

            client.putObject(PutObjectArgs.builder()
                                        .bucket(bucketName).object(path)
                                        .stream(file.getInputStream(), file.getInputStream().available(), -1)
                                        .contentType(file.getContentType())
                                        .build());
            return getUrl(path);
        }catch (Exception ex){
            throw new MinioException(ex.getMessage());
        }
    }

    @Override
    public List<URI> getFiles(String projectName) {
        var iterator = client.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());

        return StreamSupport.stream(iterator.spliterator(), false)
                .map(itemResult -> {
                    try {
                        var name = itemResult.get().objectName();
                        return URI.create(getUrl(name));
                    } catch (ErrorResponseException | InsufficientDataException | InternalException |
                             InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                             ServerException | XmlParserException e) {
                        throw new MinioException(e.getMessage());
                    }
                }).collect(Collectors.toList());
    }

    private String getUrl(String objectName){
        return urlObject.replace("?", objectName);
    }

}
