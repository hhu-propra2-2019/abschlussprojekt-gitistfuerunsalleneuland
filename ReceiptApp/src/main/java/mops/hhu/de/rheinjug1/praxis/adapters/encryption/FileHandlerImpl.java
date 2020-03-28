package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.FileHandler;
import org.springframework.stereotype.Service;

@Service
public class FileHandlerImpl implements FileHandler {
  @Override
  public void write(final String path, final String content) throws IOException {
    Files.writeString(Path.of(path), content);
  }

  @Override
  public String read(final String path) throws IOException {
    return Files.readString(Path.of(path));
  }

  @Override
  public String read(final File file) throws IOException {
    return read(file.getAbsolutePath());
  }

  @Override
  public String cerateTempFileAndGetPath() throws IOException {
    return File.createTempFile("file", ".tmp").getAbsolutePath();
  }

  @Override
  public File createTempFile() throws IOException {
    return File.createTempFile("file", ".tmp");
  }
}
