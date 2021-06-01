package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;


@Entity
public class Room {
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy="room", cascade = CascadeType.MERGE)
	private List<PointOfInterest> pois;
	
	public Room(String name, List<PointOfInterest> p) {
		this.name = name;
		this.pois = p;
	}
	
    public Room() {
		
	}
    
    public void addPoi(PointOfInterest poi) {
    	this.pois.add(poi);
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

	public List<PointOfInterest> getPois() {
		return pois;
	}

	public void setPois(List<PointOfInterest> pois) {
		this.pois = pois;
	}
    
    
}
