package atcs.museum.services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import atcs.museum.domain.*;

@Service
public class PointOfInterestService {
	
	public Long getTime(PointOfInterestVisitor poiV) {
		return ChronoUnit.MINUTES.between(poiV.getTimeOut(), poiV.getTimeIn());
	}

}
