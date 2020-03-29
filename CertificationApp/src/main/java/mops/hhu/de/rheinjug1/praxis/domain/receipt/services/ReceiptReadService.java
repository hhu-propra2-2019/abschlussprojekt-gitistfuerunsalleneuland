package mops.hhu.de.rheinjug1.praxis.domain.receipt.services;

import java.io.File;
import java.io.IOException;
import lombok.AllArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.Encoder;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.FileHandler;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.ReceiptStringConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ReceiptReadService {

  private final FileHandler fileHandler;
  private final Encoder encoder;
  private final ReceiptStringConverter converter;

  public Receipt read(final String path) throws IOException {
    final String fileText = encoder.decode(fileHandler.read(path));
    return converter.toReceipt(fileText);
  }

  public Receipt read(final MultipartFile uploadedFile) throws IOException {
    final File file = fileHandler.createTempFile();
    uploadedFile.transferTo(file);
    return read(file.getAbsolutePath());
  }
}
