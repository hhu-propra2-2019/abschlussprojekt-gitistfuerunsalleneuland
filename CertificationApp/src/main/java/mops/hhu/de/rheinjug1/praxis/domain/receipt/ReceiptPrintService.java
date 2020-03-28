package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
@RequiredArgsConstructor
public class ReceiptPrintService {

  private final FileHandler fileHandler;
  private final Encoder encoder;

  public String printReceiptAndGetPath(final Receipt receipt) throws IOException {
    final String path = fileHandler.cerateTempFileAndGetPath();
    final String yml = getYamlString(receipt);
    fileHandler.write(path, encoder.encode(yml));
    return path;
  }

  public File printReceipt(final Receipt receipt) throws IOException {
    final File file = fileHandler.createTempFile();
    final String yml = getYamlString(receipt);
    fileHandler.write(file.getAbsolutePath(), encoder.encode(yml));
    return file;
  }

  private String getYamlString(final Receipt receipt) {
    final Yaml yaml = new Yaml();
    return yaml.dumpAsMap(receipt.toDTO());
  }
}
