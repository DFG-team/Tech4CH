package atcs.museum.domain;

import java.time.LocalTime;

import javax.persistence.*;

@Entity
public class PresentationVisitor {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	@Column(name = "rate")
	private int rate;
	@Column(name = "interruption")
	private Boolean interruption;
	
	@ManyToOne
	private Visit visit;
	@ManyToOne
	private Presentation presentation;
	
	public PresentationVisitor(Presentation p, Visit v, int rate) {
		this.presentation = p;
		this.visit = v;
		this.description = presentation.getDescription();
		this.rate = rate;
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

	public int getRate() {
		return rate;
	}

	public void setEnd(int rate) {
		this.rate = rate;
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

	public Boolean getInterruption() {
		return interruption;
	}

	public void setInterruption(Boolean interruption) {
		this.interruption = interruption;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	

}
