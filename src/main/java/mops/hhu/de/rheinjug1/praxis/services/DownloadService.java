package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;

@Service
public class DownloadService {

  public String getURLforObjectDownload(final String filename)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException, MinioException, NoResponseException, ErrorResponseException, InternalException, InvalidExpiresRangeException, InvalidResponseException {
	  String url = "";
      final MinioClient minioClient = new MinioClient("http://localhost:9000/", "minio", "minio123");
      url = minioClient.presignedGetObject("rheinjug", filename);
      return url;
  }
}
