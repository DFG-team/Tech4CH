package atcs.museum.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Visit {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	
	@OneToMany(mappedBy="visit", cascade = CascadeType.ALL)
	private List<PointOfInterestVisitor> visitPois;
	@OneToMany(mappedBy="visit", cascade = CascadeType.ALL)
	private List<PresentationVisitor> visitPresentations;
	
	 @OneToOne(cascade = CascadeType.ALL)
	 private Visitor visitor;

	 public Visit() {
		 super();
		 this.visitPois = new ArrayList<>();
		 this.visitPresentations = new ArrayList<>();
	 }
	 
	 /*public Visit() {
		 
	 }*/
	 
	 public void addPresentation(PresentationVisitor p) {
		 this.visitPresentations.add(p);
	 }
	 
	 public void addPOI(PointOfInterestVisitor poi) {
		 this.visitPois.add(poi);
	 }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<PointOfInterestVisitor> getVisitPois() {
		return visitPois;
	}
	public void setVisitPois(List<PointOfInterestVisitor> visitPois) {
		this.visitPois = visitPois;
	}
	public List<PresentationVisitor> getVisitPresentations() {
		return visitPresentations;
	}
	public void setVisitPresentations(List<PresentationVisitor> visitPresentations) {
		this.visitPresentations = visitPresentations;
	}
	public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
}


