package atcs.museum.services;

import org.springframework.stereotype.Service;

import atcs.museum.domain.PointOfInterest;
import atcs.museum.domain.Presentation;
import atcs.museum.domain.Visit;

import javax.transaction.Transactional;
@Service
public class VisitService {

	@Transactional
	public void addPresentationToVisit(Presentation p,Visit v) {
		v.addPresentation(p);
	}
	
	@Transactional
	public void addPOIToVisit(PointOfInterest poi,Visit v) {
		v.addPOI(poi);
}
}
