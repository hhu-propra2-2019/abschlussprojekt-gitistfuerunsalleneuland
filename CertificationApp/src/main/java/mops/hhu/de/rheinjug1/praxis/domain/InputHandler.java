package mops.hhu.de.rheinjug1.praxis.domain;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequestMapping
@SuppressWarnings("PMD.ConfusingTernary")
@Component
@RequiredArgsConstructor
public class InputHandler {

  private static final String FALSCHE_VERANSTALTUNG = "Falsche Veranstaltung";
  private static final String FEHLERHAFTE_QUITTUNG = "Fehlerhafte Quittung";
  private static final String DOPPELT = "Doppelt";
  private static final String VALIDE = "Valide";
  private static final String KEINE_DATEI = "Keine Datei";

  private final ReceiptReaderInterface fileReaderService;
  private final ReceiptVerificationInterface verificationService;

  @Setter private String matrikelNummer;

  private List<String> signatures = new ArrayList(3);

  private Receipt firstRheinjugReceipt;
  private Receipt seccondRheinjugReceipt;
  private Receipt thirdRheinjugReceipt;
  private Receipt entwickelbarReceipt;

  private Receipt newReceipt;

  private String firstRheinjugReceiptUploadMessage = "Erste Rheinjug Quittung";
  private String seccondRheinjugReceiptUploadMessage = "Zweite Rheinjug Quittung";
  private String thirdRheinjugReceiptUploadMessage = "Dritte Rheinjug Quittung";
  private String entwickelbarReceiptUploadMessage = "Entwickelbar Quittung";

  public void setFirstRheinjugReceipt(final MultipartFile firstRheinjugFile) {
    firstRheinjugReceiptUploadMessage = getUploadMessage(firstRheinjugFile, MeetupType.RHEINJUG);
    if (firstRheinjugReceiptUploadMessage.equals(VALIDE)) {
      firstRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setSeccondRheinjugReceipt(final MultipartFile seccondRheinjugFile) {
    seccondRheinjugReceiptUploadMessage =
        getUploadMessage(seccondRheinjugFile, MeetupType.RHEINJUG);
    if (seccondRheinjugReceiptUploadMessage.equals(VALIDE)) {
      seccondRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setThirdRheinjugReceipt(final MultipartFile thirdRheinjugFile) {
    thirdRheinjugReceiptUploadMessage = getUploadMessage(thirdRheinjugFile, MeetupType.RHEINJUG);
    if (thirdRheinjugReceiptUploadMessage.equals(VALIDE)) {
      thirdRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setEntwickelbarReceipt(final MultipartFile entwickelbarFile) {
    entwickelbarReceiptUploadMessage = getUploadMessage(entwickelbarFile, MeetupType.ENTWICKELBAR);
    if (entwickelbarReceiptUploadMessage.equals(VALIDE)) {
      entwickelbarReceipt = newReceipt.cloneThisReceipt();
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

  private String getUploadMessage(final MultipartFile firstRheinjugFile, final MeetupType type) {
    try {
      newReceipt = fileReaderService.read(firstRheinjugFile);
      if (!newReceipt.getMeetupType().equals(type)) {
        return FALSCHE_VERANSTALTUNG;
      } else if (isDuplicateSignature(newReceipt.getSignature())) {
        return DOPPELT;
      } else {
        signatures.add(newReceipt.getSignature());
        return VALIDE;
      }
    } catch (IOException e) {
      if (firstRheinjugFile.isEmpty()) {
        return KEINE_DATEI;
      }
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

  public boolean verifyRheinjug()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException {
    return (verificationService.isSignatureValid(firstRheinjugReceipt)
        && verificationService.isSignatureValid(seccondRheinjugReceipt)
        && verificationService.isSignatureValid(thirdRheinjugReceipt));
  }

  public boolean verifyEntwickelbar()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException {
    return verificationService.isSignatureValid(entwickelbarReceipt);
  }
}
