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
	@ManyToOne
	private Group group;
	
	public Visitor(String name, Visit visit) {
		this.name = name;
		this.visit = visit;
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

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
}
