package mops.hhu.de.rheinjug1.praxis.services.receipt;

import java.io.IOException;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import mops.hhu.de.rheinjug1.praxis.services.MailService;
import mops.hhu.de.rheinjug1.praxis.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReceiptSendService {

  private final MailService mailService;
  private final ReceiptPrintService receiptPrintService;

  public void sendReceipt(final Receipt receipt, final String to)
      throws MessagingException, IOException {
    final String path = receiptPrintService.printReceipt(receipt);
    final String mailText = FileUtils.readStringFromFile("mail/quittung.txt");

    final String mailSubject = "Deine Quittung für " + receipt.getMeetupTitle();

    final String fileName =
        String.format(
            "%s%d-Quittung.txt", receipt.getMeetupType().getLabel(), receipt.getMeetupId());

    mailService.sendMailWithAttachment(to, mailSubject, mailText, path, fileName);
  }
}
