package mops.hhu.de.rheinjug1.praxis.domain.receipt;

import org.springframework.stereotype.Service;

@Service
public interface Encoder {
  String decode(String s);

  String encode(String s);
}
