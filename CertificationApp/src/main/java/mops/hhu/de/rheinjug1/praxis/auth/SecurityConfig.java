package mops.hhu.de.rheinjug1.praxis.auth;

import javax.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  @Autowired public KeycloakClientRequestFactory keycloakClientRequestFactory;

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) {
    final KeycloakAuthenticationProvider keycloakAuthenticationProvider =
            keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public KeycloakRestTemplate keycloakRestTemplate() {
    return new KeycloakRestTemplate(keycloakClientRequestFactory);
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Bean
  @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AccessToken getAccessToken() {
    final HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    return ((KeycloakPrincipal) request.getUserPrincipal()).getKeycloakSecurityContext().getToken();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    super.configure(http);
    forceHTTPS(http);
    http.cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/actuator/**")
            .hasRole("monitoring")
            .anyRequest()
            .permitAll();
  }

  /**
   * Redirect all Requests to SSL if header in proxy are set.
   *
   * @param http
   * @throws Exception
   */
  private void forceHTTPS(final HttpSecurity http) throws Exception {
    http.requiresChannel()
            .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
            .requiresSecure();
  }

  /**
   * Declaring this class enables us to use the Spring specific {@link
   * org.springframework.security.access.annotation.Secured} annotation or the JSR-250 Java Standard
   * {@link javax.annotation.security.RolesAllowed} annotation for Role-based authorization
   */
  @Configuration
  @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
  public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {}
}
