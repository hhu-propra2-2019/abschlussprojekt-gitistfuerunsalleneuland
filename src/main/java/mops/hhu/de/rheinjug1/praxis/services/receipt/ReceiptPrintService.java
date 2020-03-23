package mops.hhu.de.rheinjug1.praxis.services.receipt;

import static java.nio.charset.StandardCharsets.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

@Service
public class ReceiptPrintService {

  private static String getYamlString(final Receipt receipt) {
    final Representer representer = new Representer();
    representer.addClassTag(Receipt.class, Tag.MAP);

    final DumperOptions options = new DumperOptions();
    options.setIndent(2);
    options.setPrettyFlow(true);
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

    return new Yaml(representer, options).dump(receipt);
  }

  public String printReceiptAndReturnPath(final Receipt receipt) throws IOException {
    final String path = File.createTempFile("receipt", ".tmp").getAbsolutePath();

    try (FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter)) {

      fileWriter.write(Base64.toBase64String(getYamlString(receipt).getBytes(UTF_8)));

    } catch (IOException e) {
      e.printStackTrace();
    }
    return path;
  }
}
