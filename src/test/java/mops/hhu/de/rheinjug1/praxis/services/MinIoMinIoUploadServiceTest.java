package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlpull.v1.XmlPullParserException;

@SpringBootTest
public class MinIoMinIoUploadServiceTest {

  static MinioClient minioClient;

  @BeforeAll
  static void createMinioClient() throws MinioException {
    minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");
  }

  @Disabled
  @Test
  void bucketExistenceTest()
      throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          XmlPullParserException {
    assertThat(minioClient.bucketExists("rheinjug")).isTrue();
  }

  @Disabled
  @Test
  void uploadTestfile() throws Exception {
    minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");
    minioClient.putObject(
        "rheinjug", "testfile", "src/test/resources/test.txt", null, null, null, null);
    minioClient.statObject("rheinjug", "testfile");
  }
}
