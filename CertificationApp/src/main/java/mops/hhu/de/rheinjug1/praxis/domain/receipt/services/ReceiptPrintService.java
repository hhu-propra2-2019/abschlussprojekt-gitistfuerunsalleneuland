package mops.hhu.de.rheinjug1.praxis.domain.receipt.services;

import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.Encoder;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.FileHandler;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.ReceiptStringConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptPrintService {

  private final FileHandler fileHandler;
  private final Encoder encoder;
  private final ReceiptStringConverter converter;

  public String printReceiptAndReturnPath(final Receipt receipt) throws IOException {
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();
    final String fileText = converter.toString(receipt);
    fileHandler.write(path, encoder.encode(fileText));
    return path;
  }

  public File printReceiptAndGetFile(final Receipt receipt) throws IOException {
    final File file = fileHandler.createTempFile();
    final String fileText = converter.toString(receipt);
    fileHandler.write(file.getAbsolutePath(), encoder.encode(fileText));
    return file;
  }
}
