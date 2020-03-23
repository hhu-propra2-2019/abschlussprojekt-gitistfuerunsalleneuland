package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import mops.hhu.de.rheinjug1.praxis.domain.InputHandler;
import mops.hhu.de.rheinjug1.praxis.services.CertificationService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static mops.hhu.de.rheinjug1.praxis.controller.Account.createAccountFromPrincipal;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.ACCOUNT_ATTRIBUTE;
import static mops.hhu.de.rheinjug1.praxis.thymeleaf.ThymeleafAttributesHelper.INPUT_ATTRIBUTE;

@Controller
@RequestMapping("/rheinjug1")
@SuppressWarnings({
        "PMD.UnusedPrivateField",
        "PMD.SingularField",
        "PMD.UnusedImports",
        "PMD.AvoidDuplicateLiterals"
})
public class CertificationController {

    private final Counter authenticatedAccess;
    private final CertificationService certificationService = new CertificationService();
    private final Counter publicAccess;

    public CertificationController(final MeterRegistry registry) {
        authenticatedAccess = registry.counter("access.authenticated");
        publicAccess = registry.counter("access.public");
    }

    @GetMapping("/")
    @Secured({"ROLE_studentin", "ROLE_orga"})
    public String home(final KeycloakAuthenticationToken token, final Model model) {
        if (token != null) {
            final mops.hhu.de.rheinjug1.praxis.controller.Account account = createAccountFromPrincipal(token);
            model.addAttribute(ACCOUNT_ATTRIBUTE, account);
        }
        model.addAttribute(INPUT_ATTRIBUTE, new InputHandler());
        // model.addAttribute("receiptList", new VerifiedReceiptList());
        publicAccess.increment();
        return "home";
    }

    @PostMapping("/")
    @Secured({"ROLE_studentin", "ROLE_orga"})
    public String submitReceipt(
            final KeycloakAuthenticationToken token, final Model model, final InputHandler input) {

        final mops.hhu.de.rheinjug1.praxis.controller.Account account = createAccountFromPrincipal(token);
        model.addAttribute(ACCOUNT_ATTRIBUTE, account);

        model.addAttribute(INPUT_ATTRIBUTE, input);
        if (input.areRheinjugUploadsOkForCertification()) {
            certificationService.createCertification(input);
            // und sowas wie mailservice.send
        }
        if (input.isEntwickelbarUploadOkForCertification()) {
            certificationService.createCertification(input);
            // und sowas wie mailservice.send
        }
        return "home";
    }

    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
