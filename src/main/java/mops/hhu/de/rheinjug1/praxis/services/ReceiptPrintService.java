package mops.hhu.de.rheinjug1.praxis.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.springframework.stereotype.Service;

@Service
public class ReceiptPrintService {

  public String printReceipt(final Receipt receipt) {
    final String path = "src/main/resources/" + receipt.getMeetupTitle() + "-Quittung.txt";

    try (FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter)) {

      printWriter.println("Name: " + receipt.getName());
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
