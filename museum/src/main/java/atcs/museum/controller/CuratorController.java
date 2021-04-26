package atcs.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import atcs.museum.repository.VisitorRepository;
import atcs.museum.services.VisitService;
import atcs.museum.services.VisitorService;

@Controller
public class CuratorController {
	
	@Autowired
	private VisitService visitService;
	@Autowired
	private VisitorRepository visitorRepository;
	
	@Autowired
	private VisitorService visitorService;
	
	
	@RequestMapping("/")
	public String home() {
	return "index";
	}
    
	@RequestMapping("/playbackVisit")
	public String playBackVisitUser(Model model) {
		model.addAttribute("visitor",this.visitorService.getAllVisitor());
		return "playbackVisit";
	}
	

	//Html page about a visit summary for a selected visitor or group
	//For example where they visited,how much time spent, what presentation they watch
	@RequestMapping("/visitSummary")
	public String visitSummary(Model model) {
		
		return "visitSummary";
	
	}
	//Html page about statistic for all visit
	@RequestMapping("/museumStatistic")
	public String museumStatistic(Model model) {
		model.addAttribute("visit", this.visitService.getAllVisit());
		return "museumStatistic";
	
	}
}
