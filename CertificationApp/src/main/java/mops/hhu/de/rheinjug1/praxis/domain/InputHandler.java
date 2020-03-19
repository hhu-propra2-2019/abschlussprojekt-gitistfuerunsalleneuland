package mops.hhu.de.rheinjug1.praxis.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequestMapping
@SuppressWarnings("PMD.ConfusingTernary")
public class InputHandler {

  private static final String ENTWICKELBAR = "Entwickelbar";
  private static final String KEINE_ENTWICKELBAR_QUITTUNG = "Keine Entwickelbar Quittung";
  private static final String FEHLERHAFTE_QUITTUNG = "Fehlerhafte Quittung";
  private static final String DOPPELT = "Doppelt";
  private static final String KEINE_RHEINJUG_QUITTUNG = "Keine Rheinjug Quittung";
  private static final String RHEINJUG = "Rheinjug";
  private static final String VALIDE = "Valide";

  private final ReceiptService receiptService = new ReceiptService();

  private String matrikelNummer;

  private List<String> signatures = new ArrayList(3);

  private Receipt firstRheinjugReceipt;
  private Receipt seccondRheinjugReceipt;
  private Receipt thirdRheinjugReceipt;
  private Receipt entwickelbarReceipt;

  private String firstRheinjugReceiptUploadMessage = "Erste Rheinjug Quittung";
  private String seccondRheinjugReceiptUploadMessage = "Zweite Rheinjug Quittung";
  private String thirdRheinjugReceiptUploadMessage = "Dritte Rheinjug Quittung";
  private String entwickelbarReceiptUploadMessage = "Entwickelbar Quittung";

  public void setFirstRheinjugReceipt(final MultipartFile firstRheinjugFile) {
    firstRheinjugReceiptUploadMessage = getRheinjugUploadMessage(firstRheinjugFile);
  }

  public void setSeccondRheinjugReceipt(final MultipartFile seccondRheinjugFile) {
    seccondRheinjugReceiptUploadMessage = getRheinjugUploadMessage(seccondRheinjugFile);
  }

  public void setThirdRheinjugReceipt(final MultipartFile thirdRheinjugFile) {
    thirdRheinjugReceiptUploadMessage = getRheinjugUploadMessage(thirdRheinjugFile);
  }

  public void setEntwickelbarReceipt(final MultipartFile entwickelbarFile) {
    entwickelbarReceiptUploadMessage = getEntwickelbarUploadMessage(entwickelbarFile);
  }

  public boolean areRheinjugUploadsOkForCertification() {
    return firstRheinjugReceiptUploadMessage.equals(VALIDE)
        && seccondRheinjugReceiptUploadMessage.equals(VALIDE)
        && thirdRheinjugReceiptUploadMessage.equals(VALIDE);
    // &&signatures sind nicht in der Datenbank
  }

  public boolean isEntwickelbarUploadOkForCertification() {
    return entwickelbarReceiptUploadMessage.equals(VALIDE);
    // &&signature ist nicht in der Datenbank
  }

  private String getEntwickelbarUploadMessage(final MultipartFile entwickelbarFile) {
    try {
      this.entwickelbarReceipt = receiptService.read(entwickelbarFile);
      if (entwickelbarReceipt.getType().equals(ENTWICKELBAR)) {
        return VALIDE;
      } else {
        return KEINE_ENTWICKELBAR_QUITTUNG;
      }
    } catch (IOException e) {
      return FEHLERHAFTE_QUITTUNG;
    }
  }

  private String getRheinjugUploadMessage(final MultipartFile firstRheinjugFile) {
    try {
      this.firstRheinjugReceipt = receiptService.read(firstRheinjugFile);
      if (!firstRheinjugReceipt.getType().equals(RHEINJUG)) {
        return KEINE_RHEINJUG_QUITTUNG;
      } else if (isDuplicateSignature(firstRheinjugReceipt.getSignature())) {
        return DOPPELT;
      } else {
        signatures.add(firstRheinjugReceipt.getSignature());
        return VALIDE;
      }
    } catch (IOException e) {
      return FEHLERHAFTE_QUITTUNG;
    }
  }

  private boolean isDuplicateSignature(final String newSignature) {
    for (final String signature : signatures) {
      if (signature.equals(newSignature)) {
        return true;
      }
    }
    return false;
  }
}
