package demo.minio.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static java.net.URLEncoder.encode;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            log.info("Uploaded file successfully");
            return "File uploaded successfully.";
        } catch (Exception e) {
            log.error("Error occurred while uploading file ", e);
            return "Error occurred while uploading file: " + e.getMessage();
        }
    }

    public ResponseEntity<InputStreamResource> downloadFile(String filename) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );
            InputStreamResource resource = new InputStreamResource(stream);
            String encodedFilename = encode(filename, StandardCharsets.UTF_8).replace("+", "%20");

            log.debug("Successfully downloaded file: {}", encodedFilename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            log.error("Error occurred while downloading file ", e);
            return ResponseEntity.status(500).build();
        }
    }
}
