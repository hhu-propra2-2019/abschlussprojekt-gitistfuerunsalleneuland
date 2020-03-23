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
    final Yaml yaml = new Yaml();
    File tempFile = File.createTempFile("receipt", ".tmp");
    receiptFile.transferTo(tempFile);
    final Reader fileReader = new FileReader(tempFile.getAbsolutePath());
    final Receipt receipt = yaml.loadAs(fileReader, Receipt.class);
    if ("".equals(receipt.getSignature())) {
      throw new IOException();
    } else {
      return receipt;
    }
  }
}
