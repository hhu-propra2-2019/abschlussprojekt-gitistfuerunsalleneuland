package mops.hhu.de.rheinjug1.praxis.services;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class UploadService {

    private MinioClient minioClient;

    public UploadService() throws Exception {

        minioClient = new MinioClient("http://localhost:9000/",
                "minio",
                "minio123");

        if (!minioClient.bucketExists("rheinjug")) {
            minioClient.makeBucket("rheinjug");
        }
    }

    public void uploadFile(final String name, final String localFilePath)
            throws Exception {
        minioClient.putObject("rheinjug", name, localFilePath, null,
                null, null, null);
    }
}
