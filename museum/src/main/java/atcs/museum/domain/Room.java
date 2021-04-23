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

}
