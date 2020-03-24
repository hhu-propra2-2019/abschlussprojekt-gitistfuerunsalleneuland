package mops.hhu.de.rheinjug1.praxis.hex.domain.submission;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UploadService {
    void transferMultipartFileToMinIo(MultipartFile file, String filename)
        throws IOException, MinioException, XmlPullParserException, NoSuchAlgorithmException,
            InvalidKeyException;
}
