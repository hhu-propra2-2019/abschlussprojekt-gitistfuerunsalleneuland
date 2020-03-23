package mops.hhu.de.rheinjug1.praxis.services;

import static java.util.stream.Collectors.toList;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;
import mops.hhu.de.rheinjug1.praxis.MeetupType;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@SuppressWarnings({"PMD.AvoidReassigningLoopVariables", "PMD.FieldNamingConventions"})
public class FileReaderService implements ReceiptReaderInterface {

  private static final String format =
      "email: w+\n"
          + "meetupId: \\d+\n"
          + "meetupTitle: \\w+\n"
          + "meetupType: \\w+\n"
          + "name: \\w+\n"
          + "signature: .+\n";
  //   "!!mops\\.hhu\\.de\\.rheinjug1\\.praxis\\.models\\.Receipt \\{email: \\w+, meetupId: \\d+,
  // meetupTitle: \\w+,\r\n\\s+meetupType: \\w+, name: \\w+, signature: .+\\}\r\n";
  private static final String meetUpPrefix = "meetupId: ";
  private static final String typePrefix = "meetupType: ";
  private static final String signaturePrefix = "signature: ";

  @Override
  public Receipt read(final MultipartFile receiptFile) throws IOException {
    if (receiptFile == null) {
      return null;
    }
    String receiptString = "";
    try (InputStream input = receiptFile.getInputStream();
        Scanner scanner = new Scanner(input).useDelimiter("\\A"); ) {
      receiptString = scanner.hasNext() ? scanner.next() : "";
      if (isCorrectFormat(receiptString)) {
        return stringToReceipt(receiptString);
      } else {
        throw new IOException();
      }
    }
  }

  private Receipt stringToReceipt(final String rawReceiptString) {
    final Receipt receipt = new Receipt();
    final String receiptString = cut(rawReceiptString);
    final List<String> lines = receiptString.lines().collect(toList());
    for (String line : lines) {
      if (line.contains(meetUpPrefix)) {
        line = line.substring(line.indexOf(meetUpPrefix) + meetUpPrefix.length());
        receipt.setMeetupId(Long.parseLong(line));
      } else if (line.contains(typePrefix)) {
        line = line.substring(line.indexOf(typePrefix) + typePrefix.length());
        receipt.setMeetupType(MeetupType.valueOf(line));
      } else if (line.contains(signaturePrefix)) {
        line = line.substring(line.indexOf(signaturePrefix) + signaturePrefix.length());
        receipt.setSignature(line);
      }
    }
    return receipt;
  }

  private String cut(final String receiptString) {
    return receiptString.replaceAll(",", "\r\n").replaceAll("[-+^,}{]", "");
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

  public boolean verify(final Receipt receipt) {
    // Mit Signatur abgleichen
    return true;
  }
}
