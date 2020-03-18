package mops.hhu.de.rheinjug1.praxis.services.receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.springframework.stereotype.Service;

@Service
public class ReceiptPrintService {

  public String printReceipt(final Receipt receipt) throws IOException {
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();

    try (FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter)) {

      printWriter.println("Name: " + receipt.getName());
      printWriter.println("Email: " + receipt.getEmail());
      printWriter.println("Veranstaltungs-ID: " + receipt.getMeetupId());
      printWriter.println("Titel: " + receipt.getMeetupTitle());
      printWriter.println("Typ: " + receipt.getMeetupType().getLabel());
      printWriter.println(receipt.getSignature());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return path;
  }
}
