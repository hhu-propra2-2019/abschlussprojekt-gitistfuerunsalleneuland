package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("receipt")
public class ReceiptEntity {
  @Id private Long signature;
}
