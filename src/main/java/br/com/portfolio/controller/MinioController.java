package br.com.portfolio.controller;

import br.com.portfolio.dto.ResponseDefault;
import br.com.portfolio.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("storage")
public class MinioController {
    private StorageService service;

    @PostMapping
    @Operation(description = "create storage")
    public ResponseEntity<ResponseDefault> createStorage(String name){
        service.createBucket(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefault("Successfully created storage", Instant.now()));
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> fileUpload(@RequestParam String projectName, @RequestPart MultipartFile file) {
        var uri = service.uploadFile(projectName, file);

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<List<URI>> getFiles(@PathVariable("projectName") String projectName){
        var list = service.getFiles(projectName);

        return ResponseEntity.ok(list);
    }

    @PutMapping(value = "/{projectName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFile(@PathVariable("projectName") String projectName, @RequestParam String nameFile, @RequestPart MultipartFile newFile){
        var uri = service.updateFile(projectName, nameFile, newFile);

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<?> deleteFile(@PathVariable("projectName") String projectName, @RequestParam String nameFile){
        service.deleteFile(projectName, nameFile);
        return ResponseEntity.noContent().build();
    }

}
