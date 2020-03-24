package mops.hhu.de.rheinjug1.praxis.hex.domain.receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mops.hhu.de.rheinjug1.praxis.hex.domain.receipt.Receipt;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class ReceiptPrintService {

  public String printReceipt(final Receipt receipt) throws IOException {
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();

    try (FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter)) {
      final Yaml yaml = new Yaml();
      final String ymlString = yaml.dumpAsMap(receipt);
      printWriter.print(ymlString);
      return path;
    }
  }
}
