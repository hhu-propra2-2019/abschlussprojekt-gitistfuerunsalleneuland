package mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface FileHandler {
  void write(String path, String content) throws IOException;

  String read(String path) throws IOException;

  String read(File file) throws IOException;

  String cerateTempFileAndGetPath() throws IOException;

  File createTempFile() throws IOException;
}
