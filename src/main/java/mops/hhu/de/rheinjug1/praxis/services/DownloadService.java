package mops.hhu.de.rheinjug1.praxis.services;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

@Service
public class DownloadService {

  public String getURLforObjectDownload(String filename)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException {
    String url = "";
    try {

      MinioClient minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");
      url = minioClient.presignedGetObject("rheinjug", filename);
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
    }
    return url;
  }
}
