package mops.hhu.de.rheinjug1.praxis.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jlefebure.spring.boot.minio.MinioException;

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
