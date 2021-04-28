package atcs.museum.domain;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
public class Visitor {

	@Id
	private Long id;
	
	//A visitor has one visit
	@OneToOne(mappedBy ="visitor", cascade = CascadeType.ALL)
	private Visit visit;
	@ManyToOne(cascade = CascadeType.ALL)
	private GroupVisit group_visit;
	public Visitor() {
		
	}
	public Visitor(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long setId(Long id) {
		return this.id = id;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public GroupVisit getGroup() {
		return group_visit;
	}

	public void setGroup(GroupVisit group) {
		this.group_visit = group;
	}
	
}
