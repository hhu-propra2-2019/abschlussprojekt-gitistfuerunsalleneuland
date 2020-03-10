package mops.hhu.de.rheinjug1.praxis.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jlefebure.spring.boot.minio.MinioException;
import mops.hhu.de.rheinjug1.praxis.services.RheinjugMinIOService;

@Controller
public class RheinjugController {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "PMD.UnusedImports"})
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
  public String rheinjug(final KeycloakAuthenticationToken token, final Model model) {
    if (token != null) {
      model.addAttribute("account", createAccountFromPrincipal(token));
    }
    publicAccess.increment();
    return "rheinjug";
  }

  @GetMapping("/logout")
  public String logout(final HttpServletRequest request) throws ServletException {
    request.logout();
    return "redirect:/";
  }

  @GetMapping("/statistics")
  @Secured("ROLE_orga")
  public String statistics(final KeycloakAuthenticationToken token, final Model model) {
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "statistics";
  }

  @GetMapping("/talk")
  public String talk() {
    return "talk";
  }
  
  @GetMapping("/talk/{object}")
  public void downloadFile(@PathVariable("object") String object, RheinjugMinIOService rheinjugMinIOService, HttpServletResponse response) {
	  try {
		rheinjugMinIOService.getObject(object, response);
	} catch (MinioException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  
  
  @PostMapping("/talk")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RheinjugMinIOService minIOService) {
	  minIOService.upload(file);
	  return "redirect:/talk/";

  }

		
}
