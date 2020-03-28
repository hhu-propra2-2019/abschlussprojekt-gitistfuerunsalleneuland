package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.File;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

@Service
@AllArgsConstructor
public class ReceiptReadService {

  private final FileHandler fileHandler;
  private final Encoder encoder;

  public Receipt read(final String path) throws IOException {
    final String yml = encoder.decode(fileHandler.read(path));
    return getReceiptFromYml(yml);
  }

  private Receipt getReceiptFromYml(final String s) {
    return new Yaml().loadAs(s, Receipt.class);
  }

  public Receipt read(final MultipartFile uploadedFile) throws IOException {
    final File file = fileHandler.createTempFile();
    uploadedFile.transferTo(file);
    return read(file.getAbsolutePath());
  }
}
