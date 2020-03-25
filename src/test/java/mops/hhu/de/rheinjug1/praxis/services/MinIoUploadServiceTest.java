package mops.hhu.de.rheinjug1.praxis.services;

import static org.assertj.core.api.Assertions.assertThat;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import mops.hhu.de.rheinjug1.praxis.services.minio.MinIoUploadService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

@SpringBootTest
public class MinIoUploadServiceTest {

  static MinioClient minioClient;

  @Autowired private MinIoUploadService uploadService;

  @BeforeAll
  static void createMinioClient()
      throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          XmlPullParserException {
    minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");
    assertThat(minioClient.bucketExists("rheinjug")).isTrue();
  }

  @Test
  void uploadTestfile() throws Exception {
    final MultipartFile testMultipartFile =
        new MockMultipartFile(
            "test.txt", new FileInputStream(new File("src/test/resources/test.txt")));
    uploadService.transferMultipartFileToMinIo(testMultipartFile, "testfile");
    minioClient.statObject("rheinjug", "testfile");
  }

  @AfterAll
  static void deleteTestfile()
      throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IOException,
          XmlPullParserException {
    minioClient.removeObject("rheinjug", "testfile");
  }
}
