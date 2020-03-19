package mops.hhu.de.rheinjug1.praxis;

import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import mops.hhu.de.rheinjug1.praxis.models.Receipt;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

public class YAMLTest {
    @Test
    void writeYml() throws IOException {
        final Yaml yaml = new Yaml();
        final Writer fileWriter = new FileWriter("test.yml");
        final Long meetupId = 12_345L;
        final String name = "testName";
        final String email = "testEmail";
        final String meetupTitle = "testMeetupTitle";
        final String signature = "testSignature";
        final MeetupType meetupType = MeetupType.ENTWICKELBAR;
        final Receipt receipt =
                new Receipt(name, email, meetupId, meetupTitle, meetupType, signature);
        yaml.dump(receipt, fileWriter);
    }
}
