package atcs.museum.services;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.*;
import org.springframework.stereotype.Service;

import atcs.museum.domain.*;
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
	@Transactional
	public List<Visit> getAllVisit(){
		return (List<Visit>) visitRepository.findAll();
	}
	
	//**INDIVIDUAL**//
	//Play-back of individual visit
	@Transactional
	public List<PointOfInterestVisitor> getVisitPlayback(Visit v){
		return v.getVisitPois();
	}
	
	
	//**GROUP**//
	//Play-back of group visit
	@Transactional
	public HashMap<Long, List<PointOfInterestVisitor>> getGroupVisitPath(GroupVisit group){
		HashMap<Long, List<PointOfInterestVisitor>> visits = new HashMap<>();
		
		for(Visitor v: group.getVisitors()) {
			
			visits.put(v.getId(), v.getVisit().getVisitPois());
		}
		return visits;
	}
	
	
	//**INDIVIDUAL**//
	//Return a map with every POI visited by the visitor and the amount of time spent in front of the POI
	@Transactional
	public HashMap<PointOfInterestVisitor, LocalTime> getStatsPoiVisitor(Visit visit) {
		List<PointOfInterestVisitor> poisVisitor = visit.getVisitPois();
		HashMap<PointOfInterestVisitor, LocalTime> poisTime = new HashMap<>();
		
		for(PointOfInterestVisitor poiV: poisVisitor) {
			poisTime.put(poiV, (this.poiService.getTime(poiV)));
		}
		
		return poisTime;
	}
	
	
	//**INDIVIDUAL**//
	//Return a map with every presentation played by the visitor and the amount of time spent listening the presentation
	@Transactional
	public HashMap<PresentationVisitor, LocalTime> getStatsPresentationVisitor(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		HashMap<PresentationVisitor, LocalTime> pTime = new HashMap<>();
		
		for(PresentationVisitor pV: presentationVisitor) {
			pTime.put(pV, (this.pService.getTime(pV)));
		}
		return pTime;
	}
	
	
	//**INDIVIDUAL**//
	//Return a map with every presentation played by the visitor and the cause of the interruption of it(1=System, 0=User)
	@Transactional
	public HashMap<PresentationVisitor, Boolean> getStatsInterruptionPresentation(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();	
		HashMap<PresentationVisitor, Boolean> pInterruption = new HashMap<>();
		
		for(PresentationVisitor pV: presentationVisitor) {
			pInterruption.put(pV, pV.getInterruption());
		}
		return pInterruption;
	}
	
	
	//**INDIVIDUAL**//
	//Given a Visitor that has done a visit
	//It return a map with key=id of presentation played by visitor, value=rate given by visitor
	public HashMap<PresentationVisitor, Integer> getStatsRatingVisitor(Visit visit){
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		HashMap<PresentationVisitor, Integer> pRating = new HashMap<>();
		
		for(PresentationVisitor pV: presentationVisitor) {
			pRating.put(pV, pV.getRate());
		}
		return pRating;
	}
	
	
	//**INDIVIDUAL*//
	//Given a Visitor that has done a Visit
	//It return a map with key=name of the POI, value=the mean time of a group spent in front of the POI
	public HashMap<PointOfInterestVisitor,LocalTime> getMeanTimePoiGroup(Visit visit) {
		HashMap<PointOfInterestVisitor,LocalTime> meanTimePoi = new HashMap<>(); //a map with key=name of the poi, value=mean time of group in front of POI
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<PointOfInterestVisitor, LocalTime> vPois = getStatsPoiVisitor(v.getVisit()); //the pois visited by the visitor
		List<Visitor> vMates = v.getGroup().getVisitors();   //groupmates of the visitor
		
		for(PointOfInterestVisitor poi: vPois.keySet()) { //for every pois visited by the visitor
			Long temp = null;

			for(Visitor visitor: vMates) { //for every group mate of the visitor
				//temp =+ getStatsPoiVisitor(visitor.getVisit()).get(poi); //sum the time spent in front of the poi
			}
			//meanTimePoi.put(poi, temp/(vMates.size())); //put in the map the mean time(temp/sizeof group) for this poi
		}
		return meanTimePoi;
	}
	
	
	//**INDIVIDUAL**//
	//Given a Visitor that has done a Visit
	//It Return a map with key=id of presentation, value=the mean time of a group listening presentation
	public HashMap<PresentationVisitor, LocalTime> getMeanTimePresentationGroup(Visit visit) {
		HashMap<PresentationVisitor,LocalTime> meanTimePresentation = new HashMap<>(); //a map with key=name of the poi, value=mean time of group in front of POI
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<PresentationVisitor, LocalTime> vPresentation = getStatsPresentationVisitor(v.getVisit()); //the pois visited by the visitor
		List<Visitor> vMates = v.getGroup().getVisitors();   //mates of the visitor
		
		for(PresentationVisitor p: vPresentation.keySet()) {
			Long temp = null;
			for(Visitor visitor: vMates) {
				//temp =+ getStatsPresentationVisitor(visitor.getVisit()).get(p);
			}
			//meanTimePresentation.put(p, temp/(vMates.size()));
		}
		return meanTimePresentation;
	}

	
	//**INDIVIDUAL**//
	//Given a Visitor that has done a Visit
	//It return a map with key=id of presentation played by visitor, value=mean rate given by group mates of visitor
	public HashMap<PresentationVisitor,Integer> getMeanRatingGroup(Visit visit) {
		HashMap<PresentationVisitor,Integer> meanRating = new HashMap<>();
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<PresentationVisitor, Integer> vPresentation = getStatsRatingVisitor(v.getVisit());
		List<Visitor> vMates = v.getGroup().getVisitors();
		
		for(PresentationVisitor p: vPresentation.keySet()) {
			int temp = 0;
			for(Visitor visitor: vMates) {
				temp =+ getStatsRatingVisitor(visitor.getVisit()).get(p);
			}
			meanRating.put(p, temp/(vMates.size()));
		}
		return meanRating;
	}
}
