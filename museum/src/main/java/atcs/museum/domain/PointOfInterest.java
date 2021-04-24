package atcs.museum.domain;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;

@Entity
public class PointOfInterest {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "poi")
	private List<PointOfInterestVisitor> poiVisitor;

	@ManyToOne
	private Room room;
	
	public PointOfInterest(String name, Room r, List<PointOfInterestVisitor> poiV) {
		this.name = name;
		this.room = r;
		this.poiVisitor = poiV;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	public List<PointOfInterestVisitor> getPoiVisitor() {
		return poiVisitor;
	}


	public void setPoiVisitor(List<PointOfInterestVisitor> poiVisitor) {
		this.poiVisitor = poiVisitor;
	}
}
