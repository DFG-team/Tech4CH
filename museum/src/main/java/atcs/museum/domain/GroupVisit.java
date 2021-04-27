package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class GroupVisit {
	
	@Id
	Long id;
	@OneToMany(mappedBy = "group_visit", cascade = CascadeType.ALL)
	private List<Visitor> visitors;
	
	@Column(name = "size")
	private int size;
	
	public GroupVisit() {
		
	}
	public GroupVisit(List<Visitor> visitors, Long id) {
		super();
		this.id = id;
		this.size =  visitors.size();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Visitor> getVisitors() {
		return visitors;
	}
	public void setVisitors(List<Visitor> visitors) {
		this.visitors = visitors;
	}


}
