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
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.exceptions.DuplicateSignatureException;
import mops.hhu.de.rheinjug1.praxis.exceptions.SignatureDoesntMatchException;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptVerificationInterface;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptReaderService;
import mops.hhu.de.rheinjug1.praxis.services.VerificationService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Getter
@RequestMapping
@SuppressWarnings({"PMD.UnusedImports", "PMD.ConfusingTernary"})
@Component
public class InputHandler {

  private static final String FEHLERHAFTE_SIGNATUR = "Fehlerhafte Signatur";
  private static final String QUITTUNG_BEREITS_REGISTRIERT = "Quittung bereits registriert";
  private static final String FALSCHE_VERANSTALTUNG = "Falsche Veranstaltung";
  private static final String FEHLERHAFTE_QUITTUNG = "Fehlerhafte Quittung";
  private static final String DOPPELT = "Doppelt";
  private static final String VALIDE = "Valide";
  private static final String KEINE_DATEI = "Keine Datei";

  private final ReceiptReaderInterface fileReaderService = new ReceiptReaderService();
  private final ReceiptVerificationInterface verificationService = new VerificationService();

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
    firstRheinjugReceiptUploadMessage = getUploadMessage(firstRheinjugFile, MeetupType.RHEINJUG);
    if (firstRheinjugReceiptUploadMessage.equals(VALIDE)) {
      firstRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public void setSecondRheinjugReceipt(final MultipartFile seccondRheinjugFile) {
    secondRheinjugReceiptUploadMessage = getUploadMessage(seccondRheinjugFile, MeetupType.RHEINJUG);
    if (secondRheinjugReceiptUploadMessage.equals(VALIDE)) {
      secondRheinjugReceipt = newReceipt.cloneThisReceipt();
    }
  }

  public List<String> getEventTitles() {
    final List<String> eventTitles = new ArrayList<>();
    eventTitles.add(firstRheinjugReceipt.getName());
    eventTitles.add(secondRheinjugReceipt.getName());
    eventTitles.add(thirdRheinjugReceipt.getName());
    return eventTitles;
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
        && secondRheinjugReceiptUploadMessage.equals(VALIDE)
        && thirdRheinjugReceiptUploadMessage.equals(VALIDE);
    // &&signatures sind nicht in der Datenbank
  }

  public boolean isEntwickelbarUploadOkForCertification() {
    return entwickelbarReceiptUploadMessage.equals(VALIDE);
    // &&signature ist nicht in der Datenbank
  }

  private String getUploadMessage(final MultipartFile uploadedFile, final MeetupType type) {
    if (uploadedFile == null || uploadedFile.isEmpty()) {
      newReceipt = new Receipt();
      return KEINE_DATEI;
    }
    try {
      newReceipt = fileReaderService.read(uploadedFile);
      if (!newReceipt.getMeetupType().equals(type)) {
        return FALSCHE_VERANSTALTUNG;
      } else if (isDuplicateSignature(newReceipt.getSignature())) {
        return DOPPELT;
      } else {
        signatures.add(newReceipt.getSignature());
        return VALIDE;
      }
    } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
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
    try {
      verificationService.isSignatureValid(firstRheinjugReceipt);
    } catch (DuplicateSignatureException e) {
      firstRheinjugReceiptUploadMessage = QUITTUNG_BEREITS_REGISTRIERT;
      return false;
    } catch (SignatureDoesntMatchException e) {
      firstRheinjugReceiptUploadMessage = FEHLERHAFTE_SIGNATUR;
      return false;
    }
    try {
      verificationService.isSignatureValid(secondRheinjugReceipt);
    } catch (DuplicateSignatureException e) {
      secondRheinjugReceiptUploadMessage = QUITTUNG_BEREITS_REGISTRIERT;
      return false;
    } catch (SignatureDoesntMatchException e) {
      secondRheinjugReceiptUploadMessage = FEHLERHAFTE_SIGNATUR;
      return false;
    }
    try {
      verificationService.isSignatureValid(thirdRheinjugReceipt);
    } catch (DuplicateSignatureException e) {
      thirdRheinjugReceiptUploadMessage = QUITTUNG_BEREITS_REGISTRIERT;
      return false;
    } catch (SignatureDoesntMatchException e) {
      thirdRheinjugReceiptUploadMessage = FEHLERHAFTE_SIGNATUR;
      return false;
    }
    return true;
  }

  public boolean verifyEntwickelbar()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
          UnrecoverableEntryException, IOException, InvalidKeyException, SignatureException {
    try {
      return verificationService.isSignatureValid(entwickelbarReceipt);
    } catch (DuplicateSignatureException e) {
      entwickelbarReceiptUploadMessage = QUITTUNG_BEREITS_REGISTRIERT;
      return false;
    } catch (SignatureDoesntMatchException e) {
      entwickelbarReceiptUploadMessage = FEHLERHAFTE_SIGNATUR;
      return false;
    }
  }
}
