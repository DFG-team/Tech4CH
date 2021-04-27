package atcs.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import atcs.museum.repository.VisitorRepository;
import atcs.museum.services.VisitService;
import atcs.museum.services.VisitorService;

@Controller
public class VisitorController {
	
	@Autowired
	private VisitService visitService;
	@Autowired
	private VisitorRepository visitorRepository;
	
	@Autowired
	private VisitorService visitorService;
	

	@RequestMapping("/visitorPanel")
	public String playBackVisitUser(Model model) {
		model.addAttribute("visitors",this.visitorService.getAllVisitor());
		return "visitorPanel";
	}
	
	

}
