package mops.hhu.de.rheinjug1.praxis.adapters.auth;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mops.hhu.de.rheinjug1.praxis.domain.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountImpl implements Account {
  private String name;
  private String email;
  private String image;
  private Set<String> roles;

  public AccountImpl (final KeycloakAuthenticationToken token) {
    final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    this.name = principal.getName();
    this.email = principal.getKeycloakSecurityContext().getIdToken().getEmail();
    this.roles = token.getAccount().getRoles();
  }
}
