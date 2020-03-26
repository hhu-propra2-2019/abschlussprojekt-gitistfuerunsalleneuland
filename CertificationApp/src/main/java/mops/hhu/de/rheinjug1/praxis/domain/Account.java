package mops.hhu.de.rheinjug1.praxis.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  private String name;
  private String email;
  private String image;
  private Set<String> roles;

  public static Account createAccountFromPrincipal(final KeycloakAuthenticationToken token) {
    final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }
}
