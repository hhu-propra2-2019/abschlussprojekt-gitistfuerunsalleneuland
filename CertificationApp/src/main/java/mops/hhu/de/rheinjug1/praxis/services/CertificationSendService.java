package mops.hhu.de.rheinjug1.praxis.services;

import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.utils.FileReaderUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CertificationSendService {

  private final MailService mailService;

  public void sendCertificate(final byte[] certificateBytes, final String to)
      throws MessagingException, IOException {

    final File certFile = File.createTempFile("certificate", ".docx");
    FileUtils.writeByteArrayToFile(certFile, certificateBytes);
    final String path = certFile.getAbsolutePath();
    final String mailText = FileReaderUtils.readStringFromFile("mail/certificate.txt");
    final String mailSubject = "Rheinjug/Entwickelbar-Schein";
    final String fileName = "Rheinjug/Entwickelbar-Schein.docx";
    mailService.sendMailWithAttachment(to, mailSubject, mailText, path, fileName);
  }
}
