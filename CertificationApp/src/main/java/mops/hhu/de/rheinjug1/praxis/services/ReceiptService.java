package mops.hhu.de.rheinjug1.praxis.services;

import static java.util.stream.Collectors.toList;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@SuppressWarnings({"PMD.AvoidReassigningLoopVariables", "PMD.FieldNamingConventions"})
public class ReceiptService {

  private static final String format =
      "Name: \\w+\r\nVeranstaltungs-ID: \\d+\r\nTitel: \\w+\r\nTyp: \\w+\r\n.+\r\n";
  private static final String meetUpPrefix = "Veranstaltungs-ID: ";
  private static final String typePrefix = "Typ: ";

  public Receipt read(final MultipartFile receiptFile) throws IOException {
    if (receiptFile == null) {
    	System.out.println("null");
      throw new IOException();
    }
    String receiptString = "";
    try (InputStream input = receiptFile.getInputStream();
        Scanner scanner = new Scanner(input).useDelimiter("\\A"); ) {
      receiptString = scanner.hasNext() ? scanner.next() : "";
      if (isCorrectFormat(receiptString)) {
        return stringToReceipt(receiptString);
      } else {
    	  System.out.println("bad Format");
        throw new IOException();
      }
    }
  }

  private Receipt stringToReceipt(final String receiptString) {
    final Receipt receipt = new Receipt();
    final List<String> lines = receiptString.lines().collect(toList());
    for (String line : lines) {
      if (line.contains(meetUpPrefix)) {
        line = line.substring(line.indexOf(meetUpPrefix) + meetUpPrefix.length());
        receipt.setMeetupId(Long.parseLong(line));
      } else if (line.contains(typePrefix)) {
        line = line.substring(line.indexOf(typePrefix) + typePrefix.length());
        receipt.setType(line);
      } else if (!line.contains(":") && !"".equals(line)) {
        receipt.setSignature(line);
      }
    }
    return receipt;
  }

  private boolean isCorrectFormat(final String receiptString) throws IOException {
    final Pattern format = Pattern.compile(this.format);
    final Matcher matcher = format.matcher(receiptString);
    if (matcher.matches()) {
      return true;
    } else {
      throw new IOException();
    }
  }

  public boolean verify(final Receipt receipts) {
    // TODO Auto-generated method stub
    return true;
  }
}
