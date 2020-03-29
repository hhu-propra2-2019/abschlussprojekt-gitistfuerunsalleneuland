package mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces;

import javax.mail.MessagingException;

public interface MailService {
  void sendMailWithAttachment(
      String to, String subject, String text, String pathToAttachment, String filename)
      throws MessagingException;
}
