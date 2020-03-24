package mops.hhu.de.rheinjug1.praxis.domain.submission;

import io.minio.errors.MinioException;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventNotFoundException;
import mops.hhu.de.rheinjug1.praxis.domain.submission.exception.DuplicateSubmissionException;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UploadService {
    void uploadAndSaveSubmission(
            Long meetupId, MultipartFile file, Account account)
        throws MinioException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException,
            IOException, InterruptedException;

    //TODO: Diese Methode macht nichts ausser evtl eine Exception zu werfen...
    void checkUploadable(Long meetupId, Account account)
        throws EventNotFoundException, DuplicateSubmissionException;
}
