package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class StringBytesConverter {
    String toString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(new String(bytes).getBytes("UTF-8")));
    }

    byte[] toBytes(String string) throws IOException {
        return Base64.getEncoder().encode(string.getBytes());
    }
}
