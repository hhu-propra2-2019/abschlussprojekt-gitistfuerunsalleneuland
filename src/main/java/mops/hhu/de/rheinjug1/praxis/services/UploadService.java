package mops.hhu.de.rheinjug1.praxis.services;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class UploadService {
    final MinioService minioService;

    public UploadService(MinioService minioService) {
        this.minioService = minioService;
    }

    public void upload(MultipartFile file) throws MinioException, IOException {
        try (InputStream inputStream = file.getInputStream()){
            minioService.upload(Path.of(file.getName()), inputStream, ContentType.TEXT_PLAIN);
        } catch (IOException | com.jlefebure.spring.boot.minio.MinioException e) {
            throw e;
        }
    }
}