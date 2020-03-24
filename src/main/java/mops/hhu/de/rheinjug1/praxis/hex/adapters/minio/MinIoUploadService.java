package mops.hhu.de.rheinjug1.praxis.hex.adapters.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import mops.hhu.de.rheinjug1.praxis.hex.domain.submission.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

@Service
public class MinIoUploadService implements UploadService {

  @Value("${spring.minio.url}")
  private String minioUrl;

  @Value("${spring.minio.bucket}")
  private String minioBucket;

  @Value("${spring.minio.access-key}")
  private String minioAccessKey;

  @Value("${spring.minio.secret-key}")
  private String minioSecretKey;

  private void uploadFile(final String name, final String localFilePath)
      throws InvalidKeyException, NoSuchAlgorithmException, MinioException, IOException,
          XmlPullParserException {
    final MinioClient minioClient =
        new MinioClient(this.minioUrl, this.minioAccessKey, this.minioSecretKey);

    if (!minioClient.bucketExists(this.minioBucket)) {
      minioClient.makeBucket(this.minioBucket);
    }
    minioClient.putObject(this.minioBucket, name, localFilePath, null, null, null, null);
  }

  @Override
  public void transferMultipartFileToMinIo(final MultipartFile file, final String filename)
      throws IOException, MinioException, XmlPullParserException, NoSuchAlgorithmException,
          InvalidKeyException {
    final File tempFile = File.createTempFile("temp", null);
    file.transferTo(tempFile);
    uploadFile(filename, tempFile.getPath());
  }
}
