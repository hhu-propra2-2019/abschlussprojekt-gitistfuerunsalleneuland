package mops.hhu.de.rheinjug1.praxis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public final class FileReaderUtils {

  private FileReaderUtils() {}

  public static String readStringFromFile(final String fileName) throws IOException {
    try (InputStream resourceAsStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); ) {

      if (resourceAsStream == null) {
        return "";
      }
      final String text;
      text = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
      return text;
    }
  }
}
