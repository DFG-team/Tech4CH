package atcs.museum;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import atcs.museum.domain.*;
import atcs.museum.repository.*;

import org.json.simple.*;
@Component
public class DBPopulation implements ApplicationRunner{
	
	@Autowired
	private VisitorRepository visitorRepository;
	@Autowired
	private PointOfInterestRepository poiRepository;
	@Autowired
	private PointOfInterestVisitorRepository poiVRepository;
	@Autowired
	private PresentationVisitorRepository pVRepository;
	@Autowired
	private PresentationRepository pRepository;
	@Autowired
	private VisitRepository visitRepository;
	@Autowired
	private GroupRepository groupRepository;
	
	
    public void run(ApplicationArguments args) throws Exception {
        this.populateDB();
    }
	
    public void populateDB() throws IOException, InterruptedException {
    	
  
        JSONParser parser = new JSONParser();

        try {     
        	
        
        	JSONArray visitor = (JSONArray) parser.parse(new FileReader("resource/Data/T4CH_data.json"));

        	  for (Object o : visitor)
        	  {
        		  Long visitor_id = null;
        		  Long group_id = null;
        		  
        	    JSONObject v = (JSONObject) o;
                
        	    //Visitor v = new Visitor();
        	    String visitor_id_string = (String) v.get("number");
        	    
        	    //Trasform string number of visitor in a integer (provate con long se riuscite)
                try{
                    visitor_id = Long.parseLong(visitor_id_string);
                    System.out.println("\n\n\nNew Visitor:");
                    System.out.println("\nvisitor_id:" + visitor_id); // output = 146
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
        	    
        	    String group_id_string = (String) v.get("group_number");  
        	    group_id = Long.parseLong(group_id_string); 
        	    System.out.println("\ngroup_id:" + group_id);
        	    
        	    
        	    Visitor visitor_final = new Visitor(visitor_id, group_id);
        		Visit visit = new Visit();
        		GroupVisit group_visit = new GroupVisit(new ArrayList<>(), group_id);
        		visitor_final.setGroup(group_visit);
        		visitor_final.setVisit(visit);
        	    this.visitorRepository.save(visitor_final);
        	    this.visitRepository.save(visit);
        	    this.groupRepository.save(group_visit);
        	    
        	    
        	    
        	    JSONArray pointOfInterest = (JSONArray) v.get("pointOfInterests");
        	    System.out.println("\nPoi Data");

        	    for (Object c : pointOfInterest)
        	    {
        	    	JSONObject poi = (JSONObject) c;
        	    	String name = (String) poi.get("name");
        	    	System.out.println(name);
        	    	
        	    	String start_time_poi = (String) poi.get("start_time");
        	    	LocalTime start_time_VisitorPoi = LocalTime.parse(start_time_poi);
        	    	System.out.println(start_time_VisitorPoi);
        	    	
        	    	String end_time_poi = (String) poi.get("end_time");
        	    	LocalTime end_time_VisitorPoi = LocalTime.parse(end_time_poi);
        	    	System.out.println(end_time_VisitorPoi);
        	    	PointOfInterest poi_object = new PointOfInterest(name);
        	    	PointOfInterestVisitor poiV = new PointOfInterestVisitor(poi_object, visitor_final.getVisit(), start_time_VisitorPoi, end_time_VisitorPoi);
        	    	visitor_final.getVisit().addPOI(poiV);
        	    	this.poiRepository.save(poi_object);
        	    	this.poiVRepository.save(poiV);
        	      //System.out.println(c+"");
        	    }
        	    System.out.println("\nPresentation Data");
        	    JSONArray presentation = (JSONArray) v.get("presentations");
        	    for (Object d : presentation)
        	    {
        	    	JSONObject p = (JSONObject) d;
        	    	String name = (String) p.get("name");
        	    	System.out.println(name);
        	    	
        	    	String start_time_pres = (String) p.get("start_time");
        	    	LocalTime start_time_VisitorPres = LocalTime.parse(start_time_pres);
        	    	System.out.println(start_time_VisitorPres);
        	    	
        	    	String end_time_pres = (String) p.get("end_time");
        	    	LocalTime end_time_VisitorPres = LocalTime.parse(end_time_pres);
        	    	System.out.println(end_time_VisitorPres);
        	    	
        	        Long interruption =(Long) p.get("interruption");
        	        Boolean bol;
        	        if(interruption == 1)
        	        	bol=true;
        	        else {
        	        	bol=false;}
        	        System.out.println( bol);
                     
        	        
        	        String rate = (String) p.get("rate");
        	        //try{
        	        int rate_int = Integer.parseInt(rate);
        	        System.out.println(rate_int);
        	        //}
                    //catch (NumberFormatException ex){
                    //    ex.printStackTrace();
                    //}
        	        
        	    	Presentation presentation_object = new Presentation(name);
        	    	PresentationVisitor pV = new PresentationVisitor(presentation_object, visitor_final.getVisit(), rate_int, start_time_VisitorPres, end_time_VisitorPres, bol);
        	    	visitor_final.getVisit().addPresentation(pV);
        	    	this.pRepository.save(presentation_object);
        	    	this.pVRepository.save(pV);
        	      //System.out.println(c+"");
        	    }
        	    


            
        	  }
 

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}