package mops.hhu.de.rheinjug1.praxis.services;

import java.io.IOException;
import java.io.InputStream;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

// wirft Fehler, FileReaderService nicht

@Service
public class YamlReceiptReader implements ReceiptReaderInterface {

  @Override
  public Receipt read(final MultipartFile receiptFile) throws IOException {
    if (receiptFile == null) {
      return null;
    }
    try (InputStream input = receiptFile.getInputStream(); ) {
      final Constructor constructor = new Constructor(Receipt.class);
      final Yaml yaml = new Yaml(constructor);
      final Receipt receipt = (Receipt) yaml.load(input);
      if ("".equals(receipt.getSignature())) {
        throw new IOException();
      } else {
        return null;
      }
    }
  }
}
