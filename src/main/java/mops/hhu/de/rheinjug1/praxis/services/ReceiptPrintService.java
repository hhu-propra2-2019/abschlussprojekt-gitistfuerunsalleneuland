package mops.hhu.de.rheinjug1.praxis.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;

public class ReceiptPrintService {

  public void printReceipt(final Receipt receipt) {

    try (FileWriter fileWriter =
            new FileWriter("src/main/resources/" + receipt.getMeetupTitle() + "-Quittung.txt");
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
  }
}
