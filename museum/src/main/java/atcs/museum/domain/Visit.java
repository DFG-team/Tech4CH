package atcs.museum.domain;

import java.util.HashMap;

import javax.persistence.*;

@Entity
public class Visit {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	
	
	//@OneToMany
	//private HashMap<Long, PointOfInterest> visitPositions;

}
