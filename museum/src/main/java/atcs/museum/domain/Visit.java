package atcs.museum.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Visit {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	
	@OneToMany(mappedBy="visit")
	private List<PointOfInterestVisitor> visitPois;
	@OneToMany(mappedBy="visit")
	private List<PresentationVisitor> visitPresentations;
	
	 @OneToOne
	 private Visitor visitor;
	 
	 public Visit(List<PointOfInterestVisitor> vP, List<PresentationVisitor> vPr) {
		 this.visitPois = vP;
		 this.visitPresentations = vPr;
	 }
	 
	 
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


