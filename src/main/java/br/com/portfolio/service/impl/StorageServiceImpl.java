package br.com.portfolio.service.impl;

import br.com.portfolio.exception.MinioException;
import br.com.portfolio.service.StorageService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
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
    public URI uploadFile(String projectName, MultipartFile file) {
        try{
            var fileName = StringUtils.cleanPath(file.getOriginalFilename().replace(" ","_"));
            var path = String.join("/",projectName,fileName);

            client.putObject(PutObjectArgs.builder()
                                        .bucket(bucketName).object(path)
                                        .stream(file.getInputStream(), file.getInputStream().available(), -1)
                                        .contentType(file.getContentType())
                                        .build());
            return getURI(path);
        }catch (Exception ex){
            throw new MinioException(ex.getMessage());
        }
    }

    @Override
    public List<URI> getFiles(String projectName) {
        var iterator = listFiles();

        return StreamSupport.stream(iterator.spliterator(), false)
                .map(itemResult -> {
                    try {
                        var name = itemResult.get().objectName();
                        return getURI(name);
                    } catch (ErrorResponseException | InsufficientDataException | InternalException |
                             InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                             ServerException | XmlParserException e) {
                        throw new MinioException(e.getMessage());
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public URI updateFile(String projectName, String nameFile, MultipartFile newFile) {
        deleteFile(projectName, nameFile);
        return updateFile(projectName, nameFile, newFile);
    }

    @Override
    public void deleteFile(String projectName, String nameFile) {
        var path = String.join("/",projectName,nameFile);
        var list = StreamSupport.stream(listFiles().spliterator(), false)
                .filter(itemResult -> {
                    try {
                        return itemResult.get().objectName().equals(path);
                    } catch (ErrorResponseException | InsufficientDataException | InternalException |
                             InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                             ServerException | XmlParserException e) {
                        throw new MinioException(e.getMessage());
                    }
                }).toList();

        if(list.isEmpty()){
            throw new MinioException("File not exist");
        }

        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(path).build());
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    private Iterable<Result<Item>> listFiles() {
        return client.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());
    }

    private URI getURI(String objectName){
        var url = urlObject.replace("?", objectName);
        return URI.create(url);
    }

}
