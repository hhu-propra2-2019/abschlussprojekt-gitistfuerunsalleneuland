package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class ReceiptReadService {

  public Receipt read(final String path) throws IOException {
    try (Reader fileReader = new FileReader(path)) {
      final Yaml yaml = new Yaml();
      return yaml.loadAs(fileReader, Receipt.class);
    }
  }
}
