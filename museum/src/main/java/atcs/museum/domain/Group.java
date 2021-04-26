package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Group {
	
	@Id
	Long id;
	@OneToMany(mappedBy = "group")
	private List<Visitor> visitors;
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
