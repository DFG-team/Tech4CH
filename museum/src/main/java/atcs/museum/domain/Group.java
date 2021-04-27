package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Group {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	Long id;
	@OneToMany(mappedBy = "group")
	private List<Visitor> visitors;
	
	@Column(name = "size")
	private int size = visitors.size();
	
	
	
	public Group(List<Visitor> visitors) {
		this.visitors = visitors;
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
