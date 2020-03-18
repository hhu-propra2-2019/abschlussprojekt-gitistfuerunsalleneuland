package mops.hhu.de.rheinjug1.praxis.services;

import io.minio.MinioClient;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

@Service
public class UploadService {

  @Value("${spring.minio.url}")
  private String minioUrl;

  @Value("${spring.minio.bucket}")
  private String minioBucket;

  @Value("${spring.minio.access-key}")
  private String minioAccessKey;

  @Value("${spring.minio.secret-key}")
  private String minioSecretKey;

  public void uploadFile(final String name, final String localFilePath)
      throws InvalidKeyException, InvalidBucketNameException, NoSuchAlgorithmException,
          MinioException, IOException, XmlPullParserException {
    final MinioClient minioClient =
        new MinioClient(this.minioUrl, this.minioAccessKey, this.minioSecretKey);

    if (!minioClient.bucketExists(this.minioBucket)) {
      minioClient.makeBucket(this.minioBucket);
    }
    minioClient.putObject(this.minioBucket, name, localFilePath, null, null, null, null);
  }
}
