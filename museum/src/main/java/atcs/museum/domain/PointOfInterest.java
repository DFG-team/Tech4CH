package atcs.museum.domain;

import javax.persistence.*;

@Entity
public class PointOfInterest {
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
}
