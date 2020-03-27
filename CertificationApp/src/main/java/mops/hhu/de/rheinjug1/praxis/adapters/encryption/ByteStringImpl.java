package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import lombok.Getter;
import mops.hhu.de.rheinjug1.praxis.domain.ByteString;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Getter
public class ByteStringImpl implements ByteString {
    private final String string;
    private final byte[] bytes;

    public ByteStringImpl(String string) throws IOException {
        this.string = string;
        this.bytes = toBytes(string);
    }

    public ByteStringImpl(byte[] bytes) throws UnsupportedEncodingException {
        this.bytes = bytes;
        this.string = toString(bytes);
    }

    private String toString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(new String(bytes).getBytes("UTF-8")));
    }

    private byte[] toBytes(String string) throws IOException {
        return Base64.getEncoder().encode(string.getBytes());
    }
}
