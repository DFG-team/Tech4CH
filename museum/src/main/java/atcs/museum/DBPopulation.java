package atcs.museum;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import atcs.museum.domain.*;
import atcs.museum.repository.*;
import atcs.museum.repository.RoomRepository;
import org.json.simple.*;
@Component
public class DBPopulation  implements ApplicationRunner{

	@Autowired
	private VisitorRepository visitorRepository;
	@Autowired
	private RoomRepository roomRepository;
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
		this.populateRoom();
		this.populateDB();

	}



	public void populateRoom() throws IOException, InterruptedException {


		try {     

            Reader reader1 = new InputStreamReader(new ClassPathResource("Data/room.json").getInputStream());

    		JSONParser parser = new JSONParser();
            JSONArray roomIterator = (JSONArray) parser.parse(reader1);


			for (Object o : roomIterator) {
				JSONObject v = (JSONObject) o;

				String room_code = (String) v.get("room_code");

				Room room = new Room();
				room.setPois(new ArrayList<>());
				room.setName(room_code);

				JSONArray pointOfInterestRoom = (JSONArray) v.get("positions");
				for (Object c : pointOfInterestRoom) {

					JSONObject poi = (JSONObject) c;
					String name = (String) poi.get("name");

					PointOfInterest poi_object = new PointOfInterest(name);
					room.addPoi(poi_object);


				}

				this.roomRepository.save(room);
				for (Object c : pointOfInterestRoom) {

					JSONObject poi = (JSONObject) c;
					String name = (String) poi.get("name");

					PointOfInterest poi_object = new PointOfInterest(name);
					poi_object.setRoom(room);
					room.addPoi(poi_object);
					this.poiRepository.save(poi_object);


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


	public void populateDB() throws IOException, InterruptedException {


		JSONParser parser = new JSONParser();

		try {     
			
			Reader reader2 = new InputStreamReader(new ClassPathResource("Data/T4CH_data.json").getInputStream());

			JSONArray visitor = (JSONArray) parser.parse(reader2);

			Long temp_group_id = null;

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


				//Group set
				String group_id_string = (String) v.get("group_number");  
				group_id = Long.parseLong(group_id_string); 
				System.out.println("\ngroup_id:" + group_id);

				//Visitor set
				Visitor visitor_final = new Visitor(visitor_id);
				this.visitorRepository.save(visitor_final);	

				//Visit set
				Visit visit = new Visit();				
				visitor_final.setVisit(visit);
				this.visitRepository.save(visit);
				visit.setVisitor(visitor_final);


				if(group_id!=temp_group_id || temp_group_id == null) {
					temp_group_id = group_id;
					List<Visitor> visitors = new ArrayList<>();
					visitors.add(visitor_final);
					GroupVisit g = new GroupVisit(visitors, group_id);
					visitor_final.setGroup(g);
					g.setSize(g.getVisitors().size());
					this.groupRepository.save(g);	
					//int size = g.getVisitors().size();
					//this.groupRepository.updateSize(group_id, size);				

				}
				else {
					GroupVisit g1 = this.groupRepository.findById(group_id).get();

					visitor_final.setGroup(g1);
					g1.addVisitor(visitor_final);
					g1.setSize(g1.getVisitors().size());
					this.groupRepository.updateSize(group_id, g1.getVisitors().size());
					this.groupRepository.save(g1);
				}



				//POI management
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
					PointOfInterest poi_object= this.poiRepository.findByName(name);
					PointOfInterestVisitor poiV = new PointOfInterestVisitor(poi_object, visitor_final.getVisit(), start_time_VisitorPoi, end_time_VisitorPoi);
					visitor_final.getVisit().addPOI(poiV);
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
					int rate_int = Integer.parseInt(rate);
					System.out.println(rate_int);


					//System.out.println(visitor_final.getGroup().getSize());

					if(this.pRepository.findByName(name) == null) {
						Presentation presentation_object = new Presentation(name);
						this.pRepository.save(presentation_object);
						PresentationVisitor pV = new PresentationVisitor(presentation_object, visitor_final.getVisit(), rate_int, start_time_VisitorPres, end_time_VisitorPres, bol);
						visitor_final.getVisit().addPresentation(pV);
						this.pVRepository.save(pV);
					}
					else {
						Presentation presentation_object = this.pRepository.findByName(name);
						PresentationVisitor pV = new PresentationVisitor(presentation_object, visitor_final.getVisit(), rate_int, start_time_VisitorPres, end_time_VisitorPres, bol);
						visitor_final.getVisit().addPresentation(pV);
						this.pVRepository.save(pV);
					}
					//System.out.println(c+"");
				}
				/*System.out.println("\n" + visitor_final.getVisit().getVisitPois().toString());
				for(Visitor visitors_print: visitorRepository.findAll()) {
					System.out.println("\nList of group of visitor:" + visitors_print.getId());
					for(Visitor v_print: visitors_print.getGroup().getVisitors()) {
						System.out.println("\n" + v_print.getId());
					}
				}*/

				for(GroupVisit gv: this.groupRepository.findAll()) {
					System.out.println("\nNew group:"+ gv.getId() + "size:" + gv.getVisitors().size());
					for(Visitor v_print: gv.getVisitors()) {
						System.out.println("\nVisitor:"+ v_print.getId());
					}
				}


			}
			//for(GroupVisit gv:this.groupRepository.findAll()) {
			//	gv.setSize(gv.getVisitors().size());
			//}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
