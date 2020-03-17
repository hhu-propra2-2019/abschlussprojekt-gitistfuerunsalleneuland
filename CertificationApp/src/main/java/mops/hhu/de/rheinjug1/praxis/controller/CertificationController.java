package mops.hhu.de.rheinjug1.praxis.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import mops.hhu.de.rheinjug1.praxis.domain.Receipt;
import mops.hhu.de.rheinjug1.praxis.domain.ReceiptForm;
import mops.hhu.de.rheinjug1.praxis.domain.ReceiptList;
import mops.hhu.de.rheinjug1.praxis.services.ReceiptService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

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
    private final ReceiptService receiptService = new ReceiptService();
    private final Counter publicAccess;

    private final ReceiptList receiptList = new ReceiptList();

    public CertificationController(final MeterRegistry registry) {
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
    @Secured({"ROLE_student", "ROLE_orga"})
    public String uebersicht(final KeycloakAuthenticationToken token, final Model model) {
        if (token != null) {
            model.addAttribute("account", createAccountFromPrincipal(token));
        }

        model.addAttribute("receipts", new ReceiptForm());
        model.addAttribute("receiptList", receiptList);
        publicAccess.increment();
        return "uebersicht";
    }

    @PutMapping("/")
    public String revertInput(final KeycloakAuthenticationToken token, final Model model) {
        receiptList.getReceiptList().remove(0);
        System.out.println("wird ausgeführt.");
        return "redirect://";
    }

    @PostMapping("/")
    @Secured({"ROLE_student", "ROLE_orga"})
    public String submitReceipt(final KeycloakAuthenticationToken token, final Model model, final ReceiptForm receiptFiles) {
        if (token != null) {
            model.addAttribute("account", createAccountFromPrincipal(token));
        }


        if (!receiptFiles.getNewReceipt().getOriginalFilename().equals(""))
            receiptList.addNewReceipt(receiptFiles.getNewReceipt());

        model.addAttribute("receipts", receiptFiles);
        model.addAttribute("receiptList", receiptList);
        ArrayList<Receipt> receipts;
        try {
            receipts = receiptService.readAll(receiptList.getReceiptList());
        } catch (IOException e) {
            System.out.println("File has wrong a Format");
        }
        //receiptService.verify(receipts);
        return "redirect:/rheinjug1/";
    }

    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

}
