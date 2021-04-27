package atcs.museum.domain;
import javax.persistence.*;
import java.util.List;

@Entity
public class Presentation {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "presentation")
	private List<PresentationVisitor> presentationVisitor;
	
	public Presentation(String description, List<PresentationVisitor> pV) {
		this.description = description;
		this.presentationVisitor = pV;
	}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
