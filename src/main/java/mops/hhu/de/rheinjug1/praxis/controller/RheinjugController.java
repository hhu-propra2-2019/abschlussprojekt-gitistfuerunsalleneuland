package mops.hhu.de.rheinjug1.praxis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
