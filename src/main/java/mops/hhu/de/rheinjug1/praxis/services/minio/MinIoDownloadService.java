package mops.hhu.de.rheinjug1.praxis.services.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

@Service
public class MinIoDownloadService {

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
          MinioException {

    String downloadUrl = "";
    final MinioClient minioClient =
        new MinioClient(this.minioUrl, this.minioAccessKey, this.minioSecretKey);
    downloadUrl = minioClient.presignedGetObject(this.minioBucket, filename);
    return downloadUrl;
  }
}
