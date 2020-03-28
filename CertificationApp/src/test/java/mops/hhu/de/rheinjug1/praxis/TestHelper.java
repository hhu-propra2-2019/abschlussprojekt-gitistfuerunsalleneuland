package mops.hhu.de.rheinjug1.praxis;

import mops.hhu.de.rheinjug1.praxis.domain.receipt.entities.Receipt;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;

public final class TestHelper {
  public static Receipt invalidEntwickelbarReceipt() {
    return Receipt.builder()
        .meetupId(12_345L)
        .name("testName")
        .email("testEmail")
        .meetupTitle("testMeetupTitle")
        .signature("testSignature".getBytes())
        .meetupType(MeetupType.ENTWICKELBAR)
        .build();
  }

  private TestHelper() {}
}
