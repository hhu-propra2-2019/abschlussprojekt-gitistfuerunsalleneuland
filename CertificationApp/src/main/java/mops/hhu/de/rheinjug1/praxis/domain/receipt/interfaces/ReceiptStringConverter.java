package mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;

public interface ReceiptStringConverter {
  String toString(Receipt receipt);

  Receipt toReceipt(String s);
}
