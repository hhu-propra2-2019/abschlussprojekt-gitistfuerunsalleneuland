package mops.hhu.de.rheinjug1.praxis.services;

import java.io.*;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

// wirft Fehler, FileReaderService nicht

@Service
public class YamlReceiptReader implements ReceiptReaderInterface {

  @Override
  public Receipt read(final MultipartFile receiptFile) throws IOException {
    if (receiptFile == null) {
      return null;
    }
    try (InputStream inputStream = receiptFile.getInputStream()) {
      final Yaml yaml = new Yaml();
      final Receipt receipt = yaml.loadAs(inputStream, Receipt.class);
      if ("".equals(receipt.getSignature())) {
        throw new IOException();
      } else {
        return receipt;
      }
    }
  }
}
