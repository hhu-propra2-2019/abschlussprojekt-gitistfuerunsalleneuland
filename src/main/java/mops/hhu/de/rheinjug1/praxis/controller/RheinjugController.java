package mops.hhu.de.rheinjug1.praxis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mops.hhu.de.rheinjug1.praxis.services.RheinjugMinIOService;

@SuppressWarnings("PMD")
@Controller
@RequestMapping("/rheinjug1")
public class RheinjugController {

  @GetMapping("/")
  public String home() {
    return "rheinjug";
  }

  @GetMapping("/statistics")
  public String statistics() {
    return "statistics";
  }

  @GetMapping("/talk")
  public String talk() {
    return "talk";
  }
  
  @PostMapping("/talk")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RheinjugMinIOService minIOService) {
	  minIOService.upload(file);
	  
	  return "redirect:/talk/";

  }

		
}
