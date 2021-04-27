package atcs.museum;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import atcs.museum.domain.Visitor;
import atcs.museum.repository.VisitorRepository;

import org.json.simple.*;

public class jsonTrasform {
	
	@Autowired
	VisitorRepository visitorRepository;
	
	private Visitor visitor;
	
    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {     
        	
        
        	JSONArray a = (JSONArray) parser.parse(new FileReader("resource/Data/T4CH_data.json"));

        	  for (Object o : a)
        	  {
        	    JSONObject visitor = (JSONObject) o;
                
        	    //Visitor v = new Visitor();
        	    String numberString = (String) visitor.get("number");
        	    
        	    //Trasform string number of visitor in a integer (provate con long se riuscite)
                try{
                    int number = Integer.parseInt(numberString);
                    System.out.println(number); // output = 146
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
        	    
        	    String group_number = (String) visitor.get("group_number");
        	    
        	    System.out.println("\nPoi Data");
        	    JSONArray pointOfInterest = (JSONArray) visitor.get("pointOfInterests");

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
        	    	
        	    	
        	      //System.out.println(c+"");
        	    }
        	    System.out.println("\nPresentation Data");
        	    JSONArray presentation = (JSONArray) visitor.get("presentations");
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
        	        try{
        	        int rate_int = Integer.parseInt(rate);
        	        System.out.println(rate_int);
        	        }
                    catch (NumberFormatException ex){
                        ex.printStackTrace();
                    }
        	        
        	    	
        	    	
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