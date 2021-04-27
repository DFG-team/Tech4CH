package atcs.museum;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        	    String number = (String) visitor.get("number");
        	    
        	    String group_number = (String) visitor.get("group_number");
        	    
        	    JSONArray pointOfInterest = (JSONArray) visitor.get("pointOfInterests");

        	    for (Object c : pointOfInterest)
        	    {
        	      System.out.println(c+"");
        	    }
        	    
        	    System.out.println(number);
        	    System.out.println(group_number);


            
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