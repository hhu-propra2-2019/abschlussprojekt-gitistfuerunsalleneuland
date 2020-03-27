package mops.hhu.de.rheinjug1.praxis.domain.certification;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.xml.bind.JAXBException;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("PMD.AvoidCatchingGenericException")
public class CertificationService {

  private static final String TEMPLATE_NAME = "rheinjug_schein.docx";

  public byte[] createCertification(final CertificationData rheinjugCertificationData)
      throws JAXBException, Docx4JException, IOException {
    final WordprocessingMLPackage wordMLPackage;
    try (InputStream templateInputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_NAME); ) {
      wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
    }
    final MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

    try {
      VariablePrepare.prepare(wordMLPackage);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    final HashMap<String, String> variables = new HashMap<>();
    variables.put("firstName", rheinjugCertificationData.getFirstname());
    variables.put("lastName", rheinjugCertificationData.getLastname());
    variables.put("salutation", rheinjugCertificationData.getSalutation());
    variables.put("pronoun", rheinjugCertificationData.getPronoun());
    variables.put("student_number", rheinjugCertificationData.getStudentNumber());
    variables.put("event_title1", rheinjugCertificationData.getEventTitles().get(0));
    variables.put("event_title2", rheinjugCertificationData.getEventTitles().get(1));
    variables.put("event_title3", rheinjugCertificationData.getEventTitles().get(2));
    variables.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

    documentPart.variableReplace(variables);

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    wordMLPackage.save(outputStream);

    return outputStream.toByteArray();
  }
}
