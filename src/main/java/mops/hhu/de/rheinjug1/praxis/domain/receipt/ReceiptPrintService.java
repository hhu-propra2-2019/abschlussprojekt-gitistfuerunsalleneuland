package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class ReceiptPrintService {

  public String printReceiptAndReturnPath(final Receipt receipt) throws IOException {
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();

    try (FileWriter fileWriter = new FileWriter(path)) {
      fileWriter.write(Base64.toBase64String(getYamlString(receipt).getBytes(UTF_8)));
    }
    return path;
  }

  private static String getYamlString(final Receipt receipt) {
    final Yaml yaml = new Yaml();
    return yaml.dumpAsMap(receipt);
  }
}
