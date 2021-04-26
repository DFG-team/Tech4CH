package atcs.museum;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import atcs.museum.domain.Visit;
import atcs.museum.domain.Visitor;
import atcs.museum.repository.VisitorRepository;

@Component
public class DBPopulationManuale implements ApplicationRunner {
	
	@Autowired
	private VisitorRepository visitorRepository;

	public void run(ApplicationArguments args) throws Exception {
		
		//this.populateDB();
		}
	
    private void populateDB() throws IOException, InterruptedException {

        /*System.out.println("Create visitor");
        Visit visit = new Visit();
        Visitor visitor = new Visitor("Pieri", visit);
        Visitor  visitor2 = new Visitor("Fra",visit);
        Visitor  visitor3 = new Visitor("Fra",visit);
        visitor =this.visitorRepository.save(visitor);
*/
        

        System.out.println("Done.\n");
    }
}