package mops.hhu.de.rheinjug1.praxis.domain;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class InputHandlerTests {

  private static final String DOPPELT = "Doppelt";
  private static final String DUPLICATE_FILE_WASNT_RECOGNIZED = "duplicate File wasnt recognized";
  private static final String FALSCHE_VERANSTALTUNG = "Falsche Veranstaltung";
  private static final String WRONG_RECEIPT_TYPE_WASNT_RECOGNIZED =
      "wrong Receipt Type wasnt recognized";
  private static final String BAD_FILE_WASNT_RECOGNIZED = "bad File wasnt recognized";
  private static final String VALIDE = "Valide";
  private static final String FEHLERHAFTE_QUITTUNG = "Fehlerhafte Quittung";
  private static final String VALID_FILE_WASNT_RECOGNIZED = "valid File wasnt recognized";

  @Autowired InputHandler handler;

  private final MultipartFile validRheinjugFile =
      new MockMultipartFile(
          "validFile",
          ("!!mops.hhu.de.rheinjug1.praxis.models.Receipt {email: TestEmail, meetupId: 1, meetupTitle: Titel,\r\n"
                  + "  meetupType: RHEINJUG, name: TestName, signature: OEUIc5654eut}\r\n"
                  + "")
              .getBytes());
  private final MultipartFile validEntwickelbarFile =
      new MockMultipartFile(
          "validFile",
          ("!!mops.hhu.de.rheinjug1.praxis.models.Receipt {email: TestEmail, meetupId: 1, meetupTitle: Titel,\r\n"
                  + "  meetupType: ENTWICKELBAR, name: TestName, signature: OEUIc5654eut}\r\n"
                  + "")
              .getBytes());
  private final MultipartFile invalidFile =
      new MockMultipartFile(
          "validFile",
          ("!!mops.hhu.de.rheinjug1.praxis.models.Receipt {email: TestEmail, meetupId:, meetupTitle: Titel,\r\n"
                  + "  meetupType: ENTWICKELBAR, name: TestName, signature: OEUIc5654eut}")
              .getBytes());

  @AfterEach
  public void resetHandler() {
    handler.resetSignatures();
  }

  @Test
  public void isFirstUploadMessageFalscheVeranstaultung() {
    handler.setFirstRheinjugReceipt(validEntwickelbarFile);
    assertEquals(
        WRONG_RECEIPT_TYPE_WASNT_RECOGNIZED,
        FALSCHE_VERANSTALTUNG,
        handler.getFirstRheinjugReceiptUploadMessage());
  }

  @Test
  public void isFirstUploadMessageFehlerhafteQuittung() {
    handler.setFirstRheinjugReceipt(invalidFile);
    assertEquals(
        BAD_FILE_WASNT_RECOGNIZED,
        FEHLERHAFTE_QUITTUNG,
        handler.getFirstRheinjugReceiptUploadMessage());
  }

  @Test
  public void isFirstUploadMessageValide() {
    handler.setFirstRheinjugReceipt(validRheinjugFile);
    assertEquals(
        VALID_FILE_WASNT_RECOGNIZED, VALIDE, handler.getFirstRheinjugReceiptUploadMessage());
  }

  @Test
  public void isSeccondUploadMessageFalscheVeranstaultung() {
    handler.setSeccondRheinjugReceipt(validEntwickelbarFile);
    assertEquals(
        WRONG_RECEIPT_TYPE_WASNT_RECOGNIZED,
        FALSCHE_VERANSTALTUNG,
        handler.getFirstRheinjugReceiptUploadMessage());
  }

  @Test
  public void isSeccondUploadMessageFehlerhafteQuittung() {
    handler.setSeccondRheinjugReceipt(invalidFile);
    assertEquals(
        BAD_FILE_WASNT_RECOGNIZED,
        FEHLERHAFTE_QUITTUNG,
        handler.getSeccondRheinjugReceiptUploadMessage());
  }

  @Test
  public void isSeccondUploadMessageValide() {
    handler.setSeccondRheinjugReceipt(validRheinjugFile);
    assertEquals(
        VALID_FILE_WASNT_RECOGNIZED, VALIDE, handler.getSeccondRheinjugReceiptUploadMessage());
  }

  @Test
  public void isThirdUploadMessageFalscheVeranstaultung() {
    handler.setThirdRheinjugReceipt(validEntwickelbarFile);
    assertEquals(
        WRONG_RECEIPT_TYPE_WASNT_RECOGNIZED,
        FALSCHE_VERANSTALTUNG,
        handler.getThirdRheinjugReceiptUploadMessage());
  }

  @Test
  public void isThirdUploadMessageFehlerhafteQuittung() {
    handler.setThirdRheinjugReceipt(invalidFile);
    assertEquals(
        BAD_FILE_WASNT_RECOGNIZED,
        FEHLERHAFTE_QUITTUNG,
        handler.getThirdRheinjugReceiptUploadMessage());
  }

  @Test
  public void isThirdUploadMessageValide() {
    handler.setThirdRheinjugReceipt(validRheinjugFile);
    assertEquals(
        VALID_FILE_WASNT_RECOGNIZED, VALIDE, handler.getThirdRheinjugReceiptUploadMessage());
  }

  @Test
  public void isUploadMessageDoppelt() {
    handler.setFirstRheinjugReceipt(validRheinjugFile);
    handler.setThirdRheinjugReceipt(validRheinjugFile);
    assertEquals(
        DUPLICATE_FILE_WASNT_RECOGNIZED, DOPPELT, handler.getThirdRheinjugReceiptUploadMessage());
  }

  @Test
  public void isEntwickelbarUploadMessageFalscheVeranstaultung() {
    handler.setEntwickelbarReceipt(validRheinjugFile);
    assertEquals(
        WRONG_RECEIPT_TYPE_WASNT_RECOGNIZED,
        FALSCHE_VERANSTALTUNG,
        handler.getEntwickelbarReceiptUploadMessage());
  }

  @Test
  public void isEntwickelbarUploadMessageFehlerhafteQuittung() {
    handler.setEntwickelbarReceipt(invalidFile);
    assertEquals(
        BAD_FILE_WASNT_RECOGNIZED,
        FEHLERHAFTE_QUITTUNG,
        handler.getEntwickelbarReceiptUploadMessage());
  }

  @Test
  public void isEntwickelbarUploadMessageValide() {
    handler.setEntwickelbarReceipt(validEntwickelbarFile);
    assertEquals(
        VALID_FILE_WASNT_RECOGNIZED, VALIDE, handler.getEntwickelbarReceiptUploadMessage());
  }
}
