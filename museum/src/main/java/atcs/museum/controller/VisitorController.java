package atcs.museum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import atcs.museum.domain.PointOfInterest;
import atcs.museum.domain.PointOfInterestVisitor;
import atcs.museum.domain.Visitor;
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

	int double_touch;

	@RequestMapping("/visitorPanel")
	public String playBackVisit(Model model) {
		model.addAttribute("visitors",this.visitorService.getAllVisitor());
		return "visitorPanel";
	}

	@RequestMapping("visitorPlayback/visitor{IdV}")
	public String playBackVisitUser(@PathVariable("IdV")Long idV, Model model) {
		Visitor v = this.visitorRepository.findById(idV).get();

		model.addAttribute("singleVisitor",v); 
		model.addAttribute("playbackPois", visitService.getVisitPlayback(v.getVisit()));
		model.addAttribute("doubleTime", visitService.getDoubleVisit(v.getVisit()));
		model.addAttribute("namePois", this.visitService.getNamePois());


		return "visitorPlayback";
	}


}
