package mops.hhu.de.rheinjug1.praxis.adapters.encryption;

import mops.hhu.de.rheinjug1.praxis.domain.ByteString;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ByteStringTest {
    @Test
    void decodingEncodedGivesOriginalString() throws IOException {
        final ByteString byteString = new ByteStringImpl("TestString");
        assertThat(new ByteStringImpl(byteString.getBytes()).getString())
                .isEqualTo(byteString.getString());
    }

    @Test
    void encodingDecodedGivesOriginalBytes() throws IOException {
        final ByteString byteString = new ByteStringImpl("TestString");
        assertThat(new ByteStringImpl(byteString.getString()).getBytes())
                .isEqualTo(byteString.getBytes());
    }
}