package mops.hhu.de.rheinjug1.praxis.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rheinjug1")
public class rheinjugController {
	
	@GetMapping("/")
	public String home() {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}
	
	@GetMapping("/statistiken")
	public String statistiken() {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}
	
	@GetMapping("/vortrag")
	public String vortrag() {
		throw new java.lang.UnsupportedOperationException("Not supported yet.");
	}

}
