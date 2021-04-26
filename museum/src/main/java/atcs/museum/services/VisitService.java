package atcs.museum.services;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.*;
import org.springframework.stereotype.Service;

import atcs.museum.domain.Group;
import atcs.museum.domain.PointOfInterest;
import atcs.museum.domain.PointOfInterestVisitor;
import atcs.museum.domain.Presentation;
import atcs.museum.domain.PresentationVisitor;
import atcs.museum.domain.Visit;
import atcs.museum.domain.Visitor;
import atcs.museum.repository.VisitRepository;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;
@Service
public class VisitService {
	
	@Autowired
	private VisitRepository visitRepository;
	@Autowired
	private PointOfInterestService poiService;
	@Autowired
	private PresentationService pService;
	
	@Transactional
	public Visit getVisitByVisitor(Visitor visitor) {
		return this.visitRepository.findByVisitor(visitor);
	}
	
	//Play-back of individual visit
	@Transactional
	public List<PointOfInterestVisitor> getVisitPlayback(Visit v){
		return v.getVisitPois();
	}
	
	//Play-back of group visit
	@Transactional
	public HashMap<Long, List<PointOfInterestVisitor>> getGroupVisitPath(Group group){
		HashMap<Long, List<PointOfInterestVisitor>> visits = new HashMap<>();
		for(Visitor v: group.getVisitors()) {
			
			visits.put(v.getId(), v.getVisit().getVisitPois());
		}
		return visits;
	}
	
	//Return a map with every POI visited by the visitor and the amount of time spent in front of the POI
	@Transactional
	public HashMap<String, Long> getStatsPoiVisitor(Visit visit) {
		List<PointOfInterestVisitor> poisVisitor = visit.getVisitPois();
		
		HashMap<String, Long> poisTime = new HashMap<>();
		for(PointOfInterestVisitor poiV: poisVisitor) {
			poisTime.put(poiV.getName(), (this.poiService.getTime(poiV)));
		}
		
		return poisTime;
		
	}
	
	//Return a map with every presentation played by the visitor and the amount of time spent listening the presentation
	@Transactional
	public HashMap<Long, Long> getStatsPresentationVisitor(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		
		HashMap<Long, Long> pTime = new HashMap<>();
		for(PresentationVisitor pV: presentationVisitor) {
			pTime.put(pV.getId(), (this.pService.getTime(pV)));
		}
		
		return pTime;
	}
	
	//Return a map with every presentation played by the visitor and the cause of the interruption of it(1=System, 0=User)
	@Transactional
	public HashMap<Long, Boolean> getStatsInterruptionPresentation(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		
		HashMap<Long, Boolean> pInterruption = new HashMap<>();
		for(PresentationVisitor pV: presentationVisitor) {
			pInterruption.put(pV.getId(), pV.getInterruption());
		}
		return pInterruption;
	}
	
	
	
	//Return a map with key=name of the POI, value=the mean time of a group spent in front of the POI
	public HashMap<String,Long> getMeanTimePoiGroup(Visit visit) {
		HashMap<String,Long> meanTimePoi = new HashMap<>(); //a map with key=name of the poi, value=mean time of group in front of POI
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<String, Long> vPois = getStatsPoiVisitor(v.getVisit()); //the pois visited by the visitor
		List<Visitor> vMates = v.getGroup().getVisitors();   //mates of the visitor
		for(String namePoi: vPois.keySet()) { //for every pois visited by the visitor
			Long temp = null;
			for(Visitor visitor: vMates) { //for every group mate of the visitor
				temp =+ getStatsPoiVisitor(visitor.getVisit()).get(namePoi); //sum the time spent in front of the poi
			}
			meanTimePoi.put(namePoi, temp/(vMates.size())); //put in the map the mean time(temp/sizeof group) for this poi
		}
		return meanTimePoi;
	}
	
	
	//Return a map with key=id of presentation, value=the mean time of a group listening presentation
	public HashMap<Long, Long> getMeanTimePresentationGroup(Visit visit) {
		HashMap<Long,Long> meanTimePresentation = new HashMap<>(); //a map with key=name of the poi, value=mean time of group in front of POI
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<Long, Long> vPresentation = getStatsPresentationVisitor(v.getVisit()); //the pois visited by the visitor
		List<Visitor> vMates = v.getGroup().getVisitors();   //mates of the visitor
		for(Long idPresentation: vPresentation.keySet()) {
			Long temp = null;
			for(Visitor visitor: vMates) {
				temp =+ getStatsPresentationVisitor(visitor.getVisit()).get(idPresentation);
			}
			meanTimePresentation.put(idPresentation, temp/(vMates.size()));
		}
		return meanTimePresentation;
	}
}
