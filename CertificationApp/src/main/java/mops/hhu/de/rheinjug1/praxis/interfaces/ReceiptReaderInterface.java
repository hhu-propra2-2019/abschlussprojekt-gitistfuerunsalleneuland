package mops.hhu.de.rheinjug1.praxis.interfaces;

import java.io.IOException;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.exceptions.Base64Exception;

import org.springframework.web.multipart.MultipartFile;

public interface ReceiptReaderInterface {

  Receipt read(final MultipartFile file) throws IOException, Base64Exception;
}
