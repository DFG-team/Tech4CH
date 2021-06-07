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
import atcs.museum.repository.*;
import atcs.museum.repository.PointOfInterestVisitorRepository;
import atcs.museum.repository.PresentationVisitorRepository;
import atcs.museum.repository.VisitRepository;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;
@Service
public class VisitService {

	@Autowired
	private VisitRepository visitRepository;
	@Autowired
	private PointOfInterestRepository poiRepository;
	@Autowired
	private PresentationRepository pRepository;
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
	@Transactional
	public HashMap<Long, String> getNamePois(){
		List<PointOfInterest> pois = (List<PointOfInterest>)this.poiRepository.findAll();
		HashMap<Long, String> namePois = new HashMap<>();
		for(PointOfInterest poi: pois) {
			namePois.put(poi.getId(), poi.getName());
		}
		return namePois;
	}
	@Transactional
	public HashMap<Long, String> getNamePres(){
		List<Presentation> pres = (List<Presentation>)this.pRepository.findAll();
		HashMap<Long, String> namePres = new HashMap<>();
		for(Presentation p: pres) {
			namePres.put(p.getId(), p.getName());
		}
		return namePres;
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
	public HashMap<Long, LocalTime> getStatsPoiVisitor(Visit visit) {
		List<PointOfInterestVisitor> poisVisitor = visit.getVisitPois();
		HashMap<Long, LocalTime> poisTime = new HashMap<>();

		for(PointOfInterestVisitor poiV: poisVisitor) {
			if(poisTime.containsKey(poiV.getPoi().getId())){
				LocalTime temp = poisTime.get(poiV.getPoi().getId());
				poisTime.put(poiV.getPoi().getId(), temp.plusNanos(this.poiService.getTime(poiV).toNanoOfDay()));
			}
			else {
				poisTime.put(poiV.getPoi().getId(), (this.poiService.getTime(poiV)));
			}
		}

		return poisTime;
	}


	//**INDIVIDUAL**//
	//Return a map with every presentation played by the visitor and the amount of time spent listening the presentation
	@Transactional
	public HashMap<Long, LocalTime> getStatsPresentationVisitor(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		HashMap<Long, LocalTime> pTime = new HashMap<>();

		for(PresentationVisitor pV: presentationVisitor) {
			if(pTime.containsKey(pV.getPresentation().getId())) {
				LocalTime temp = pTime.get(pV.getPresentation().getId());
				pTime.put(pV.getPresentation().getId(), (temp.plusNanos(this.pService.getTime(pV).toNanoOfDay())));
			}
			else
				pTime.put(pV.getPresentation().getId(), this.pService.getTime(pV));
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
	public HashMap<Long,LocalTime> getMeanTimePoiGroup(Visit visit) {
		HashMap<Long,LocalTime> meanTimePoi = new HashMap<>(); //a map with key=name of the poi, value=mean time of group in front of POI
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<Long, LocalTime> vPois = getStatsPoiVisitor(v.getVisit()); //the pois visited by the visitor
		List<Visitor> vMates = v.getGroup().getVisitors();   //groupmates of the visitor
		int cont;
		for(Long id: vPois.keySet()) { //for every pois visited by the visitor
			Long temp = null;
			cont = 0;

			for(Visitor visitor: vMates) { //for every group mate of the visitor
				if(getStatsPoiVisitor(visitor.getVisit()).containsKey(id)) {
					temp =+ getStatsPoiVisitor(visitor.getVisit()).get(id).toNanoOfDay(); //sum the time spent in front of the poi
					cont =+ 1;
				}
			}
			meanTimePoi.put(id, LocalTime.ofNanoOfDay(temp/cont)); //put in the map the mean time(temp/sizeof group) for this poi
		}
		return meanTimePoi;
	}


	//**INDIVIDUAL**//
	//Given a Visitor that has done a Visit
	//It Return a map with key=id of presentation, value=the mean time of a group listening presentation
	public HashMap<Long, LocalTime> getMeanTimePresentationGroup(Visit visit) {
		HashMap<Long,LocalTime> meanTimePresentation = new HashMap<>(); //a map with key=name of the poi, value=mean time of group in front of POI
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<Long, LocalTime> vPresentation = getStatsPresentationVisitor(v.getVisit()); //the pois visited by the visitor
		List<Visitor> vMates = v.getGroup().getVisitors();   //mates of the visitor
		int cont = 0;
		for(Long id: vPresentation.keySet()) {
			Long temp = null;
			cont = 0;
			for(Visitor visitor: vMates) {
				if(getStatsPresentationVisitor(visitor.getVisit()).containsKey(id)) {
					temp =+ getStatsPresentationVisitor(visitor.getVisit()).get(id).toNanoOfDay();
					cont =+ 1;
				}
			}
			meanTimePresentation.put(id, LocalTime.ofNanoOfDay(temp/cont));
		}
		return meanTimePresentation;
	}


	//**INDIVIDUAL**//
	//Given a Visitor that has done a Visit
	//It return a map with key=id of presentation played by visitor, value=mean rate given by group mates of visitor
	public HashMap<Long,Integer> getMeanRatingGroup(Visit visit) {
		HashMap<Long,Integer> meanRating = new HashMap<>();
		Visitor v = visit.getVisitor();  //the visitor
		HashMap<PresentationVisitor, Integer> vPresentation = getStatsRatingVisitor(v.getVisit());
		List<Visitor> vMates = v.getGroup().getVisitors();
		int cont = 0;
		for(PresentationVisitor p: vPresentation.keySet()) {
			int temp = 0;
			cont = 0;
			for(Visitor visitor: vMates) {
				if(getStatsRatingVisitor(visitor.getVisit()).containsKey(p)) {
					temp =+ getStatsRatingVisitor(visitor.getVisit()).get(p);
					cont =+ 1;
				}
			}
			meanRating.put(p.getPresentation().getId(), (temp/cont));
		}
		return meanRating;
	}
}
