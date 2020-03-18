package mops.hhu.de.rheinjug1.praxis.services;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

@Service
public class DownloadService {

  @Value("${spring.minio.url}")
  private String minioUrl;

  @Value("${spring.minio.bucket}")
  private String minioBucket;

  @Value("${spring.minio.access-key}")
  private String minioAccessKey;

  @Value("${spring.minio.secret-key}")
  private String minioSecretKey;

  public String getURLforObjectDownload(final String filename)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException,
          MinioException, NoResponseException, ErrorResponseException, InternalException,
          InvalidExpiresRangeException, InvalidResponseException {

    String downloadUrl = "";
    final MinioClient minioClient =
        new MinioClient(this.minioUrl, this.minioAccessKey, this.minioSecretKey);
    downloadUrl = minioClient.presignedGetObject(this.minioBucket, filename);
    return downloadUrl;
  }
}
