package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.MinioException;

@Service
public class UploadService {

  private final MinioClient minioClient;

  public UploadService() throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException, XmlPullParserException {

    minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");

    if (!minioClient.bucketExists("rheinjug")) {
      minioClient.makeBucket("rheinjug");
    }
  }

  public void uploadFile(final String name, final String localFilePath) throws InvalidKeyException, InvalidBucketNameException, NoSuchAlgorithmException, MinioException, IOException, XmlPullParserException {
    minioClient.putObject("rheinjug", name, localFilePath, null, null, null, null);
  }
}
