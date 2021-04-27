package atcs.museum.domain;

import javax.persistence.*;

@Entity
public class Visitor {

	@Id
	private Long id;
	@Column(name = "id_group")
	private Long idGroup;
	
	//A visitor has one visit
	@OneToOne(mappedBy ="visitor")
	private Visit visit;
	@ManyToOne
	private Group group;
	
	public Visitor(int id, Visit visit) {
		
		this.visit = visit;
	}

	public Long getId() {
		return id;
	}

	public Long setId(Long id) {
		return this.id = id;
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
