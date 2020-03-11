package mops.hhu.de.rheinjug1.praxis.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SuppressWarnings({
  "PMD.UnusedPrivateField",
  "PMD.SingularField",
  "PMD.UnusedImports",
  "PMD.AvoidDuplicateLiterals"
})
public class RheinjugController {

  private final Counter authenticatedAccess;

  private final Counter publicAccess;

  public RheinjugController(final MeterRegistry registry) {
    authenticatedAccess = registry.counter("access.authenticated");
    publicAccess = registry.counter("access.public");
  }

  private Account createAccountFromPrincipal(final KeycloakAuthenticationToken token) {
    final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }

  @GetMapping("/")
  public String uebersicht(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));}
    publicAccess.increment();
    return "uebersicht";
  }
  
  @GetMapping("/talk")
  @Secured("ROLE_orga")
  public String statistics(final KeycloakAuthenticationToken token, final Model model) {
	  if (token != null) model.addAttribute("account", createAccountFromPrincipal(token));
	  model.addAttribute("summaryForm", new Summary()); 
      return "talk";
  }
  
  
  @PostMapping("/talk")
  @Secured({"ROLE_student", "ROLE_orga"})
	public String handleFileUpload(@RequestParam("summary") MultipartFile file) {
	  return "redirect:/talk/";

  }
  
  @GetMapping("/logout") 
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }

  

  @GetMapping("/profil")
  public String profil(final KeycloakAuthenticationToken token, final Model model) {
	  if (token != null) model.addAttribute("account", createAccountFromPrincipal(token));
	   return "profil";
  }

  @GetMapping("/statistics")
  public String talk(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) model.addAttribute("account", createAccountFromPrincipal(token));
    return "statistics";
  }

  

		
}
