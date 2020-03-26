package mops.hhu.de.rheinjug1.praxis.adapters.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MinIoDownloadServiceTest {

  static MinioClient minioClient;

  @Autowired private MinIoDownloadService downloadService;

  private static String filename = "downloadTestFile";

  @BeforeAll
  static void createMinioClient()
      throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          XmlPullParserException {
    minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");
    assertThat(minioClient.bucketExists("rheinjug")).isTrue();
    minioClient.putObject(
        "rheinjug", filename, "src/test/resources/test.txt", null, null, null, null);
    minioClient.statObject("rheinjug", filename);
  }

  @Test
  void downloadTestfile()
      throws InvalidKeyException, NoSuchAlgorithmException, MinioException, IOException,
          XmlPullParserException {
    final URL downloadUrl = new URL(downloadService.getURLforDownload(filename));
    final URLConnection connection = downloadUrl.openConnection();
    final String contentType = connection.getContentType();
    assertThat(contentType).isEqualTo("text/plain");
  }
}
