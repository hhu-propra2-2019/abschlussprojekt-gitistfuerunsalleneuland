package mops.hhu.de.rheinjug1.praxis.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import mops.hhu.de.rheinjug1.praxis.services.FileReaderService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequestMapping
@SuppressWarnings("PMD.ConfusingTernary")
@Component
public class InputHandler {

  private static final String FALSCHE_VERANSTALTUNG = "Falsche Veranstaltung";
  private static final String ENTWICKELBAR = "ENTWICKELBAR";
  private static final String FEHLERHAFTE_QUITTUNG = "Fehlerhafte Quittung";
  private static final String DOPPELT = "Doppelt";
  private static final String RHEINJUG = "RHEINJUG";
  private static final String VALIDE = "Valide";
  private static final String KEINE_DATEI = "Keine Datei";

  private final ReceiptReaderInterface fileReaderService = new FileReaderService();

  @Setter private String matrikelNummer;

  private List<String> signatures = new ArrayList(3);

  private Receipt firstRheinjugReceipt;
  private Receipt secondRheinjugReceipt;
  private Receipt thirdRheinjugReceipt;
  private Receipt entwickelbarReceipt;

  private Receipt newReceipt;

  private String firstRheinjugReceiptUploadMessage = "Erste Rheinjug Quittung";
  private String secondRheinjugReceiptUploadMessage = "Zweite Rheinjug Quittung";
  private String thirdRheinjugReceiptUploadMessage = "Dritte Rheinjug Quittung";
  private String entwickelbarReceiptUploadMessage = "Entwickelbar Quittung";

  public void setFirstRheinjugReceipt(final MultipartFile firstRheinjugFile) {
    firstRheinjugReceiptUploadMessage = getUploadMessage(firstRheinjugFile, RHEINJUG);
    if (firstRheinjugReceiptUploadMessage.equals(VALIDE)) {
      firstRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setSecondRheinjugReceipt(final MultipartFile seccondRheinjugFile) {
    secondRheinjugReceiptUploadMessage = getUploadMessage(seccondRheinjugFile, RHEINJUG);
    if (secondRheinjugReceiptUploadMessage.equals(VALIDE)) {
      secondRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setThirdRheinjugReceipt(final MultipartFile thirdRheinjugFile) {
    thirdRheinjugReceiptUploadMessage = getUploadMessage(thirdRheinjugFile, RHEINJUG);
    if (thirdRheinjugReceiptUploadMessage.equals(VALIDE)) {
      thirdRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setEntwickelbarReceipt(final MultipartFile entwickelbarFile) {
    entwickelbarReceiptUploadMessage = getUploadMessage(entwickelbarFile, ENTWICKELBAR);
    if (entwickelbarReceiptUploadMessage.equals(VALIDE)) {
      entwickelbarReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public boolean areRheinjugUploadsOkForCertification() {
    return firstRheinjugReceiptUploadMessage.equals(VALIDE)
        && secondRheinjugReceiptUploadMessage.equals(VALIDE)
        && thirdRheinjugReceiptUploadMessage.equals(VALIDE);
    // &&signatures sind nicht in der Datenbank
  }

  public boolean isEntwickelbarUploadOkForCertification() {
    return entwickelbarReceiptUploadMessage.equals(VALIDE);
    // &&signature ist nicht in der Datenbank
  }

  private String getUploadMessage(final MultipartFile uploadedFile, final String type) {
    if (uploadedFile.isEmpty()) {
      return KEINE_DATEI;
    }
    try {
      newReceipt = fileReaderService.read(uploadedFile);
      if (!newReceipt.getType().equals(type)) {
        return FALSCHE_VERANSTALTUNG;
      } else if (isDuplicateSignature(newReceipt.getSignature())) {
        return DOPPELT;
      } else {
        signatures.add(newReceipt.getSignature());
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

  public void resetSignatures() {
    signatures.clear();
  }
}
