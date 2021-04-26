package atcs.museum.services;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import atcs.museum.domain.*;

@Service
public class PresentationService {
	
	public Long getTime(PresentationVisitor pV) {
		return ChronoUnit.MINUTES.between(pV.getTimeOff(), pV.getTimeOn());
	}
}
