package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
<<<<<<< HEAD:src/main/java/mops/hhu/de/rheinjug1/praxis/domain/receipt/ReceiptPrintService.java
import java.io.PrintWriter;
=======
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.bouncycastle.util.encoders.Base64;
>>>>>>> master:src/main/java/mops/hhu/de/rheinjug1/praxis/services/receipt/ReceiptPrintService.java
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class ReceiptPrintService {

  public String printReceiptAndReturnPath(final Receipt receipt) throws IOException {
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();

<<<<<<< HEAD:src/main/java/mops/hhu/de/rheinjug1/praxis/domain/receipt/ReceiptPrintService.java
    try (FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter)) {
      final Yaml yaml = new Yaml();
      final String ymlString = yaml.dumpAsMap(receipt);
      printWriter.print(ymlString);
      return path;
=======
    try (FileWriter fileWriter = new FileWriter(path)) {
      fileWriter.write(Base64.toBase64String(getYamlString(receipt).getBytes(UTF_8)));
>>>>>>> master:src/main/java/mops/hhu/de/rheinjug1/praxis/services/receipt/ReceiptPrintService.java
    }
  }

  private static String getYamlString(final Receipt receipt) {
    final Yaml yaml = new Yaml();
    return yaml.dumpAsMap(receipt);
  }
}
