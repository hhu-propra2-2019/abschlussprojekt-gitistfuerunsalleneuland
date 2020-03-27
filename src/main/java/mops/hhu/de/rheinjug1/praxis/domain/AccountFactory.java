package mops.hhu.de.rheinjug1.praxis.domain;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

public interface AccountFactory {
  Account createFromPrincipal(KeycloakAuthenticationToken token);
}
