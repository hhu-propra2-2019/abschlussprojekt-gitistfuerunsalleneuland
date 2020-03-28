package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.ReceiptStringConverter;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class ReceiptToYmlConverter implements ReceiptStringConverter {
  @Override
  public String toString(final Receipt receipt) {
    return new Yaml().dumpAsMap(new ReceiptDTO(receipt));
  }

  @Override
  public Receipt toReceipt(final String s) {
    return new Yaml().loadAs(s, Receipt.class);
  }
}
