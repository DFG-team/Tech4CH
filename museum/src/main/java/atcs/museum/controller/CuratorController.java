package atcs.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CuratorController {
	
	
	@RequestMapping("/")
	public String home() {
	return "index";
	}

}
