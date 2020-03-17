package mops.hhu.de.rheinjug1.praxis.services;

import javax.mail.MessagingException;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.springframework.stereotype.Service;

@Service
public class ReceiptSendService {
  private static final String TEXT =
      "Liebe Teilnehmerin / Lieber Teilnehmer, \n\n"
          + "anbei erhälst Du deine Quittung für die Teilnahme "
          + "an deiner Meetup-Veranstaltung und Abgabe einer gelungenen Zusammenfassung!\n\n"
          + "Mit freundlichen Grüßen\n"
          + "Dein Rheinjug-Team";

  private final MailService mailService;
  private final ReceiptPrintService receiptPrintService;

  public ReceiptSendService(
      final MailService mailService, final ReceiptPrintService receiptPrintService) {
    this.mailService = mailService;
    this.receiptPrintService = receiptPrintService;
  }

  public void sendReceipt(final Receipt receipt, final String to) throws MessagingException {
    final String path = receiptPrintService.printReceipt(receipt);

    mailService.sendMailWithAttachment(
        to,
        "Deine Quittung für " + receipt.getMeetupTitle(),
        TEXT,
        path,
        receipt.getMeetupTitle() + "-Quittung.txt");
  }
}
