package atcs.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import atcs.museum.domain.Visitor;
import atcs.museum.repository.GroupRepository;
import atcs.museum.repository.PointOfInterestVisitorRepository;
import atcs.museum.repository.PresentationVisitorRepository;
import atcs.museum.repository.VisitorRepository;
import atcs.museum.services.MuseumStatsService;
import atcs.museum.services.VisitService;
import atcs.museum.services.VisitorService;

@Controller
public class CuratorController {
	
	@Autowired
	private VisitService visitService;
	@Autowired
	private VisitorService visitorService;
	@Autowired
	private MuseumStatsService museumStatsService;
	@Autowired
	private VisitorRepository visitorRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private PointOfInterestVisitorRepository poiRepository;
	@Autowired
	private PresentationVisitorRepository pRepository;
	
	
	@RequestMapping("/")
	public String home() {
	return "index";
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
	@RequestMapping("/visitorsList/visitor{IdV}")
	public String visitorList(@PathVariable("IdV")Long idV, Model model) {
		Visitor v = this.visitorRepository.findById(idV).get();
		//PLAYBACK
		model.addAttribute("visitor", v);
		model.addAttribute("pois", this.visitorService.getPois(idV));
		model.addAttribute("presentations", this.visitorService.getPres(idV));
		model.addAttribute("namePois", this.visitService.getNamePois());
		model.addAttribute("namePres", this.visitService.getNamePres());
		
		//STATS
		model.addAttribute("statsPoi", this.visitService.getStatsPoiVisitor(v.getVisit()));
		model.addAttribute("statsP", this.visitService.getStatsPresentationVisitor(v.getVisit()));
		model.addAttribute("statsRating", this.visitService.getStatsRatingVisitor(v.getVisit()));
		model.addAttribute("statsInterruption", this.visitService.getStatsInterruptionPresentation(v.getVisit()));
		
	    //GROUP AVERAGE
		model.addAttribute("meanPoi", this.visitService.getMeanTimePoiGroup(v.getVisit()));
		model.addAttribute("meanPresentation", this.visitService.getMeanTimePresentationGroup(v.getVisit()));
		model.addAttribute("meanRating", this.visitService.getMeanRatingGroup(v.getVisit()));
		
		return "visitSummary";
	}
	
	
	//Html page about statistic for all visit
	@RequestMapping("/museumStatistic")
	public String museumStatistic(Model model) {
		model.addAttribute("perHour", this.museumStatsService.getVisitorsPerHour());
		model.addAttribute("perRoomPerHour", this.museumStatsService.getVisitorsPerRoomPerHour());
		model.addAttribute("attractionPower", this.museumStatsService.attractionPowerStats());
		model.addAttribute("holdingPower", this.museumStatsService.holdingPowerStats());
		model.addAttribute("namePois", this.visitService.getNamePois());
		return "museumStatistic";
	
	}
}
