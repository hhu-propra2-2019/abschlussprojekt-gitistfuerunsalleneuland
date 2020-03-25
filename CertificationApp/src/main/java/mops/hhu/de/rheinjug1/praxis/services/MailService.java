package mops.hhu.de.rheinjug1.praxis.services;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  public final JavaMailSender mailSender;

  public MailService(final JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendMailWithAttachment(
      final String to,
      final String subject,
      final String text,
      final String pathToAttachment,
      final String filename)
      throws MessagingException {

    final MimeMessage message = mailSender.createMimeMessage();

    final MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text);

    final FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
    helper.addAttachment(filename, file);

    mailSender.send(message);
  }
}