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
    try {
      this.firstRheinjugReceipt = receiptService.read(firstRheinjugFile);
      if (!firstRheinjugReceipt.getType().equals(RHEINJUG)) {
        firstRheinjugReceiptUploadMessage = KEINE_RHEINJUG_QUITTUNG;
      } else if (isDuplicateSignature(firstRheinjugReceipt.getSignature())) {
        firstRheinjugReceiptUploadMessage = DOPPELT;
      } else {
        signatures.add(firstRheinjugReceipt.getSignature());
        firstRheinjugReceiptUploadMessage = VALIDE;
      }
    } catch (IOException e) {
      firstRheinjugReceiptUploadMessage = FEHLERHAFTE_QUITTUNG;
    }
  }

  public void setSeccondRheinjugReceipt(final MultipartFile seccondRheinjugFile) {
    try {
      this.seccondRheinjugReceipt = receiptService.read(seccondRheinjugFile);
      if (!seccondRheinjugReceipt.getType().equals(RHEINJUG)) {
        seccondRheinjugReceiptUploadMessage = KEINE_RHEINJUG_QUITTUNG;
      } else if (isDuplicateSignature(seccondRheinjugReceipt.getSignature())) {
        seccondRheinjugReceiptUploadMessage = DOPPELT;
      } else {
        signatures.add(seccondRheinjugReceipt.getSignature());
        seccondRheinjugReceiptUploadMessage = VALIDE;
      }
    } catch (IOException e) {
      seccondRheinjugReceiptUploadMessage = FEHLERHAFTE_QUITTUNG;
    }
  }

  public void setThirdRheinjugReceipt(final MultipartFile thirdRheinjugFile) {
    try {
      this.thirdRheinjugReceipt = receiptService.read(thirdRheinjugFile);
      if (!thirdRheinjugReceipt.getType().equals(RHEINJUG)) {
        thirdRheinjugReceiptUploadMessage = KEINE_RHEINJUG_QUITTUNG;
      } else if (isDuplicateSignature(thirdRheinjugReceipt.getSignature())) {
        thirdRheinjugReceiptUploadMessage = DOPPELT;
      } else {
        signatures.add(thirdRheinjugReceipt.getSignature());
        thirdRheinjugReceiptUploadMessage = VALIDE;
      }
    } catch (IOException e) {
      thirdRheinjugReceiptUploadMessage = FEHLERHAFTE_QUITTUNG;
    }
  }

  public void setEntwickelbarReceipt(final MultipartFile entwickelbarFile) {
    try {
      this.entwickelbarReceipt = receiptService.read(entwickelbarFile);
      if (entwickelbarReceipt.getType().equals(ENTWICKELBAR)) {
        entwickelbarReceiptUploadMessage = VALIDE;
      } else {
        entwickelbarReceiptUploadMessage = KEINE_ENTWICKELBAR_QUITTUNG;
      }
    } catch (IOException e) {
      entwickelbarReceiptUploadMessage = FEHLERHAFTE_QUITTUNG;
    }
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

  private boolean isDuplicateSignature(final String newSignature) {
    for (final String signature : signatures) {
      if (signature.equals(newSignature)) {
        return true;
      }
    }
    return false;
  }
}
