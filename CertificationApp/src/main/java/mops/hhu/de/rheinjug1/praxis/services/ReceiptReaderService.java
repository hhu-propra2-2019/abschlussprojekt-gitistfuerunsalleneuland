package mops.hhu.de.rheinjug1.praxis.services;

import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.interfaces.ReceiptReaderInterface;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service
public class ReceiptReaderService implements ReceiptReaderInterface {

  @Override
  public Receipt read(final MultipartFile base64ReceiptFile) throws IOException {

    if (base64ReceiptFile == null) {
      throw new IOException();
    }
    try (InputStream input = deCryptBase64(base64ReceiptFile)) {
      final Constructor constructor = new Constructor(Receipt.class);
      final Yaml yaml = new Yaml(constructor);
      final Receipt receipt = (Receipt) yaml.load(input);
      if (receipt == null || "".equals(receipt.getSignature())) {
        throw new IOException();
      } else {
        return receipt;
      }
    }
  }

  private InputStream deCryptBase64(final MultipartFile base64ReceiptFile) throws IOException {
    final String receiptString;
    try (InputStream input = base64ReceiptFile.getInputStream();
        Scanner scanner = new Scanner(input).useDelimiter("\\A"); ) {
      receiptString = scanner.hasNext() ? scanner.next() : "";
    }
    final byte[] receiptBytes = Base64.decode(receiptString);
    return new ByteArrayInputStream(receiptBytes);
  }
}
