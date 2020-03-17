package mops.hhu.de.rheinjug1.praxis.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReceiptForm {

  private String matrikelNummer;
  private MultipartFile newReceipt;

}
