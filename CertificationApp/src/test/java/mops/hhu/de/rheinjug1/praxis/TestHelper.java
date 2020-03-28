package mops.hhu.de.rheinjug1.praxis;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.receipt.interfaces.EncryptionService;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

@Component
public final class TestHelper implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public static Receipt sampleEntwickelbarReceipt() throws InvalidKeyException, IOException, SignatureException {
    final Receipt receipt = Receipt.builder()
            .meetupId(12_345L)
            .name("testName")
            .email("testEmail")
            .meetupTitle("testMeetupTitle")
            .meetupType(MeetupType.ENTWICKELBAR)
            .build();
    final EncryptionService encryptionService = applicationContext.getBean(EncryptionService.class);
    encryptionService.sign(receipt);
    return receipt;
  }

  private TestHelper() {}

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
}
