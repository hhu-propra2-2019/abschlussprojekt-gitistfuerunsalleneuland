package mops.hhu.de.rheinjug1.praxis.adapters.auth;

import mops.hhu.de.rheinjug1.praxis.domain.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AccountFactoryImpl implements mops.hhu.de.rheinjug1.praxis.domain.AccountFactory {
  @Override
  public Account createFromPrincipal(final KeycloakAuthenticationToken token) {
    final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return AccountImpl.builder()
        .name(principal.getName())
        .email(principal.getKeycloakSecurityContext().getIdToken().getEmail())
        .roles(token.getAccount().getRoles())
        .build();
  }
}
