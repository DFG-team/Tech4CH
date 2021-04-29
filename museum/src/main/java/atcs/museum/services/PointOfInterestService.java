package atcs.museum.services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import atcs.museum.domain.*;

@Service
public class PointOfInterestService {
	
	public LocalTime getTime(PointOfInterestVisitor poiV) {
		return poiV.getTimeOut().minusNanos(poiV.getTimeIn().toNanoOfDay());
	}

}
