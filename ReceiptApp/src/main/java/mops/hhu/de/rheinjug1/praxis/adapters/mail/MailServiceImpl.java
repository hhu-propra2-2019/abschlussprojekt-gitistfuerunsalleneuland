package mops.hhu.de.rheinjug1.praxis.adapters.mail;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.MailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
  public final JavaMailSender mailSender;

  public MailServiceImpl(final JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
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
