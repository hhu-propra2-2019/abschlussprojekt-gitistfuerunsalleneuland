package mops.hhu.de.rheinjug1.praxis.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class Receipt {
    @Id
    private Long id;
    private boolean used;

    public Receipt() {
    }
}
