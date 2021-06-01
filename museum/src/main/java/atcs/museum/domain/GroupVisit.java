package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.Modifying;

@Entity
@DynamicUpdate(true) 
public class GroupVisit {
	
	@Id
	Long id;
	@OneToMany(mappedBy = "group_visit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Visitor> visitors;
	
	@Column(name = "size")
	private int size;

	public GroupVisit(List<Visitor> visitors, Long id) {
		super();
		this.id = id;
		this.visitors = visitors;
	}
	
	public GroupVisit() {
		
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
	public void addVisitor(Visitor v) {
		this.visitors.add(v);
	}
	public int getSize() {
		return this.size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void updateSize() {
		this.size = this.visitors.size();
	}


}
