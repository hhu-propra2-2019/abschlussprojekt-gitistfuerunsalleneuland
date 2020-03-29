package mops.hhu.de.rheinjug1.praxis.domain.receipt.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("receipt")
public class SignatureRecord {
  @Id private byte[] signature;
}
