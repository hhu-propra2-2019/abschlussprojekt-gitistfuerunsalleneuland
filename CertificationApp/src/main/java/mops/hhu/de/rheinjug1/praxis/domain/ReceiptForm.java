package mops.hhu.de.rheinjug1.praxis.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReceiptForm {

  private String matrikelNummer;
  private List<MultipartFile> receiptList = new ArrayList<>();
  private MultipartFile newReceipt;

  public void addReceipt() {
    if (newReceipt != null) {
      receiptList.add(newReceipt);
    }
  }
}
