package mops.hhu.de.rheinjug1.praxis.adapters.auth;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountImpl implements Account {
  private String name;
  private String email;
  private String image;
  private Set<String> roles;
}
