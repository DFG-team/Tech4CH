package atcs.museum.services;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.*;
import org.springframework.stereotype.Service;

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
	
	//Play-back with PointOfInterest list (speed-up missing)
	@Transactional
	public List<PointOfInterestVisitor> getVisitPlayback(Visit v){
		return v.getVisitPois();
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
	public HashMap<Long, Boolean> getInterruptionPresentation(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		
		HashMap<Long, Boolean> pInterruption = new HashMap<>();
		for(PresentationVisitor pV: presentationVisitor) {
			pInterruption.put(pV.getId(), pV.getInterruption());
		}
		return pInterruption;
	}
	
}
