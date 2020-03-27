package mops.hhu.de.rheinjug1.praxis.domain.submission;

import io.minio.errors.InternalException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.xmlpull.v1.XmlPullParserException;

public interface DownloadService {
  String getURLforDownload(String filename)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException,
          MinioException, InternalException, InvalidExpiresRangeException, InvalidResponseException;
}
