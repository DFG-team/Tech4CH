package atcs.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import atcs.museum.repository.GroupRepository;
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
	private GroupRepository groupRepository;
	@Autowired
	private VisitorService visitorService;
	
	
	@RequestMapping("/")
	public String home() {
	return "homePage";
	}
    
	@RequestMapping("/curatorPanel")
	public String curatorPanel() {
	return "curatorPanel";
	}
	//Html page about a visit summary for a selected visitor or group
	//For example where they visited,how much time spent, what presentation they watch
	@RequestMapping("/groupsList")
	public String visitSummary(Model model) {
		model.addAttribute("groups", this.groupRepository.findAll());
		
		return "groupsList";
	
	}
	@RequestMapping("/groupsList/group{IdG}")
	public String groupList(@PathVariable("IdG")Long idG, Model model) {
		model.addAttribute("visitors", this.groupRepository.findById(idG).get().getVisitors());
		return "visitorsList";
	}
	//Html page about statistic for all visit
	@RequestMapping("/museumStatistic")
	public String museumStatistic(Model model) {
		model.addAttribute("visit", this.visitService.getAllVisit());
		return "museumStatistic";
	
	}
}
