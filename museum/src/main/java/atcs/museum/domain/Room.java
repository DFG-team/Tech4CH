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
	@OneToMany(mappedBy="room")
	private List<PointOfInterest> pois;
	
	public Room(String name, List<PointOfInterest> p) {
		this.name = name;
		this.pois = p;
	}
}
