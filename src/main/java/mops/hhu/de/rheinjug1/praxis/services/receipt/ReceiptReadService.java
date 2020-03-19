package mops.hhu.de.rheinjug1.praxis.services.receipt;

import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
public class ReceiptReadService {

  public Receipt read(final String path) throws IOException {
    try (Reader fileReader = new FileReader(path)) {
      final Yaml yaml = new Yaml();
      return (Receipt) yaml.load(fileReader);

    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new IOException();
  }
}
