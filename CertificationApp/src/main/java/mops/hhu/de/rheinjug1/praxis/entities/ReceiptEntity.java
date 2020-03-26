package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.hhu.de.rheinjug1.praxis.enums.MeetupType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Data
@Table("receipt")
public class ReceiptEntity {
  @Id long id;
  private String signature;
  private MeetupType meetupType;
}