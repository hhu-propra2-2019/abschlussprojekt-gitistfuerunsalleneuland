package mops.hhu.de.rheinjug1.praxis.adapters.database.receipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignatureDTO {

    @Id
    private String signature;
    private Long meetupId;
}
