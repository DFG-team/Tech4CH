package atcs.museum.domain;

import javax.persistence.*;

@Entity
public class Visitor {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "id_group")
	private Long idGroup;
	
	//A visitor has one visit
	@OneToOne(mappedBy ="visitor")
	private Visit visit;
	public Visitor(String name, Visit visit) {
		this.name = name;
		this.visit = visit;
	}
}
