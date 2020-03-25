package mops.hhu.de.rheinjug1.praxis.interfaces;

import java.io.IOException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import org.springframework.web.multipart.MultipartFile;

public interface ReceiptReaderInterface {

  Receipt read(final MultipartFile file) throws IOException;
}
