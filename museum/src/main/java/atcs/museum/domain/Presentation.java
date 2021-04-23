package atcs.museum.domain;
import javax.persistence.*;
import java.time.*;

@Entity
public class Presentation {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	//time when user start presentation
	@Column(name = "time_on")
	private LocalTime timeOn;
	//time when user stop presentation
	@Column(name = "time_off")
	private LocalTime timeOff;
	//end of presentation:System or User
	@Column(name = "end")
	private String end;
	
	@ManyToOne
	private Visit visit;

}
