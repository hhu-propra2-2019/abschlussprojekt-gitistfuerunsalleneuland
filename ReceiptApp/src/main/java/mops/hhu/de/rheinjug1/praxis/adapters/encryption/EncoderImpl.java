package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.Encoder;
import org.springframework.stereotype.Service;

@Service
public class EncoderImpl implements Encoder {
  @Override
  public String decode(final String s) {
    return new String(decodeBase64(s.getBytes(UTF_8)));
  }

  @Override
  public String encode(final String s) {
    return new String(encodeBase64(s.getBytes(UTF_8)));
  }
}
