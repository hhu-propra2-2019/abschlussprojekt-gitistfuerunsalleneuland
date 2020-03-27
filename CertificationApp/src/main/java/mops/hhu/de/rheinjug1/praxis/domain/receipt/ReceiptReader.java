package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ReceiptReader {

  ReceiptDTO read(final MultipartFile file)
      throws IOException, NoSuchFieldException, IllegalAccessException;
}
