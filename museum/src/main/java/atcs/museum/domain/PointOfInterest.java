package atcs.museum.domain;

import java.util.List;
import javax.persistence.*;

@Entity
public class PointOfInterest {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	@Column(name = "name")
	private String name;


	@ManyToOne(fetch = FetchType.EAGER)
	private Room room;
	
	public PointOfInterest(String name) {
		this.name = name;
	}
	public PointOfInterest() {
		
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
}
