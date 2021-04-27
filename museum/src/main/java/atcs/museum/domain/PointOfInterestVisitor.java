package atcs.museum.domain;

import java.time.LocalTime;

import javax.persistence.*;

@Entity
public class PointOfInterestVisitor {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	//time when visitor arrives on POI
	@Column(name = "time_in")
	private LocalTime timeIn;
	//time when visitor leaves the POI
	@Column(name = "time_out")
	private LocalTime timeOut;
	@Column(name = "name")
	private String name;
	@OneToOne
	private PointOfInterest poi;
	@ManyToOne
	private Visit visit;
	
	public PointOfInterestVisitor(PointOfInterest poi, Visit v, LocalTime t1, LocalTime t2) {
		this.poi = poi;
		this.visit = v;
		this.id = this.poi.getId();
		this.name = this.poi.getName();
		this.timeIn = t1;
		this.timeOut = t2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(LocalTime timeIn) {
		this.timeIn = timeIn;
	}

	public LocalTime getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(LocalTime timeOut) {
		this.timeOut = timeOut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PointOfInterest getPoi() {
		return poi;
	}

	public void setPoi(PointOfInterest poi) {
		this.poi = poi;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}
	

}
