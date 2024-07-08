package demo.minio.controller;

import demo.minio.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/minio")
@Slf4j
@RequiredArgsConstructor
public class MinioController {

    private final MinioService minioService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to upload file: {}", file);
        return minioService.uploadFile(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) {
        log.debug("REST request to download file with name: {}", filename);
        return minioService.downloadFile(filename);
    }
}
