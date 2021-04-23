package atcs.museum.domain;

import java.time.LocalTime;

import javax.persistence.*;

@Entity
public class PointOfInterest {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	//time when visitor arrives on POI
	@Column(name = "time_in")
	private LocalTime timeIn;
	//time when visitor leaves the POI
	@Column(name = "time_out")
	private LocalTime timeOut;
	
	@ManyToOne
	private Visit visit;
	@ManyToOne
	private Room room;
}
