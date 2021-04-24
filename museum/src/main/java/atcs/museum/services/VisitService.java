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
	@Transactional
	public void addPresentationToVisit(PresentationVisitor p,Long idVisit) {
		Visit v = visitRepository.findById(idVisit).get();
		v.addPresentation(p);
	}
	
	@Transactional
	public void addPOIToVisit(PointOfInterestVisitor poi,Long idVisit) {
		Visit v = visitRepository.findById(idVisit).get();
		v.addPOI(poi);
	}
	@Transactional
	public List<PointOfInterestVisitor> getVisitPath(Visit v){
		return v.getVisitPois();
	}
	
	//@Transactional
	//public List<PresentationVisitor> getVisitPresentations(Visit v){
	//	return v.getVisitPresentations();
	//}
	
	@Transactional
	public void getStatsPoiVisitor(Visit visit) {
		List<PointOfInterestVisitor> poisVisitor = visit.getVisitPois();
		
		HashMap<String, Long> poisTime = new HashMap<>();
		for(PointOfInterestVisitor poiV: poisVisitor) {
			poisTime.put(poiV.getName(), (this.poiService.getTime(poiV)));
		}
		
	}
	@Transactional
	public void getStatsPresentationVisitor(Visit visit) {
		List<PresentationVisitor> presentationVisitor = visit.getVisitPresentations();
		
		HashMap<Long, Long> pTime = new HashMap<>();
		for(PresentationVisitor pV: presentationVisitor) {
			pTime.put(pV.getId(), (this.pService.getTime(pV)));
		}
		
	}
	
}
