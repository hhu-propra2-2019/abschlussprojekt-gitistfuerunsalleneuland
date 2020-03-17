package mops.hhu.de.rheinjug1.praxis.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class VerifiedReceiptList {

  private List<Receipt> receiptList = new ArrayList<>();
  private List<String> signatures = new ArrayList<>();

  public void addNewReceipt(final Receipt newReceipt) {
    if (!isDuplicateSignature(newReceipt.getSignature())) {
      receiptList.add(newReceipt);
      signatures.add(newReceipt.getSignature());
    }
  }

  private boolean isDuplicateSignature(final String newSignature) {
    for (final String signature : signatures) {
      if (signature.equals(newSignature)) {
        return true;
      }
    }
    return false;
  }
}
