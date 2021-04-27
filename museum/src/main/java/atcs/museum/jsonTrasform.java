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
        	    
        	    JSONArray pointOfInterest = (JSONArray) visitor.get("pointOfInterests");

        	    for (Object c : pointOfInterest)
        	    {
        	    	JSONObject poi = (JSONObject) c;
        	    	String name = (String) poi.get("name");
        	    	System.out.println("name");
        	    	
        	      System.out.println(c+"");
        	    }
        	    
        	    
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