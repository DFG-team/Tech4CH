package atcs.museum.domain;

import java.time.LocalTime;

import javax.persistence.*;

@Entity
public class PresentationVisitor {
	
	@Id
	private Long id;
	
	@Column(name = "description")
	private String description;
	//time when user start presentation
	@Column(name = "time_on")
	private LocalTime timeOn;
	//time when user stop presentation
	@Column(name = "time_off")
	private LocalTime timeOff;
	//end of presentation:System or User
	@Column(name = "finish")
	private Boolean end;
	
	@ManyToOne
	private Visit visit;
	@ManyToOne
	private Presentation presentation;
	
	public PresentationVisitor(Presentation p, Visit v, Boolean end) {
		this.presentation = p;
		this.visit = v;
		this.id = presentation.getId();
		this.description = presentation.getDescription();
		this.end = end;
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

	public LocalTime getTimeOn() {
		return timeOn;
	}

	public void setTimeOn(LocalTime timeOn) {
		this.timeOn = timeOn;
	}

	public LocalTime getTimeOff() {
		return timeOff;
	}

	public void setTimeOff(LocalTime timeOff) {
		this.timeOff = timeOff;
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Presentation getPresentation() {
		return presentation;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
	}
	

}
