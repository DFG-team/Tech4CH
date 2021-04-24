package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Visit {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	
	@OneToMany(mappedBy="visit")
	private List<PointOfInterest> visitPois;
	@OneToMany(mappedBy="visit")
	private List<Presentation> visitPresentations;
	
	 @OneToOne
	 private Visitor visitor;
	 
	 
	 public void addPresentation(Presentation p) {
		 this.visitPresentations.add(p);
	 }
	 
	 public void addPOI(PointOfInterest poi) {
		 this.visitPois.add(poi);
	 }
}


