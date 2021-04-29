package atcs.museum.services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit.*;

import org.springframework.stereotype.Service;

import atcs.museum.domain.*;

import static java.time.temporal.ChronoUnit.MINUTES;
@Service
public class PresentationService {
	
	public LocalTime getTime(PresentationVisitor pV) {
		//Long time = pV.getTimeOn().until(pV.getTimeOff(), MINUTES);
		//String ti = Long.toString(time);
		//LocalTime t = LocalTime.parse(ti);
		
		return pV.getTimeOff().minusNanos(pV.getTimeOn().toNanoOfDay());
	}
}
