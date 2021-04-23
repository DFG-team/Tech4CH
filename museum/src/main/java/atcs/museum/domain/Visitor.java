package atcs.museum.domain;

import javax.persistence.*;

@Entity
public class Visitor {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	//A visitor has one visit
	@OneToOne
	private Visit visit;
}
