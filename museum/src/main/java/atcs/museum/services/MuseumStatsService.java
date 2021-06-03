package atcs.museum.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import atcs.museum.domain.*;
import atcs.museum.repository.*;

@Service
public class MuseumStatsService {
	@Autowired
	private VisitRepository visitRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private PointOfInterestRepository poiRepository;
	@Autowired
	private VisitService visitService;



	/*MUSEUM STATISTICS PER HOUR*/
	public TreeMap<Integer, Integer> getVisitorsPerHour(){
		List<Visit> visits = (List<Visit>) visitRepository.findAll();
		HashMap<Integer,Integer> visitorPerHour = new HashMap<>();
		TreeMap<Integer,Integer> sorted = new TreeMap<>();
		for(Visit v: visits) {
			List<PointOfInterestVisitor> poiV = v.getVisitPois();
			int temp = 0;
			for(PointOfInterestVisitor poi: poiV) {
				int hour = poi.getTimeIn().getHour();
				if(temp != hour){
					if(visitorPerHour.containsKey(hour))
						visitorPerHour.put(hour, visitorPerHour.get(hour) + 1);
					else
						visitorPerHour.put(hour, 1);
					temp = hour;
				}
			}
		}
		sorted.putAll(visitorPerHour);
		return sorted;	
	}



	/*MUSEUM STATISTICS PER ROOM PER HOUR*/
	public HashMap<Room, TreeMap<Integer, Integer>> getVisitorsPerRoomPerHour(){
		List<Visit> visits = (List<Visit>) visitRepository.findAll();
		HashMap<Room,TreeMap<Integer, Integer>> visitorsPerRoomPerHour = new HashMap<>();
		for(Visit v: visits) {
			List<PointOfInterestVisitor> poiV = v.getVisitPois();
			HashMap<Room,Integer> temp = new HashMap<>();
			for(PointOfInterestVisitor poi: poiV) {
				Room r = poi.getPoi().getRoom();
				int hour = poi.getTimeIn().getHour();
				if(!temp.containsKey(r) || (temp.containsKey(r) && temp.get(r) != hour)) {
					temp.put(r, hour);
					if(!visitorsPerRoomPerHour.containsKey(r)) {
						TreeMap<Integer,Integer> perHour = new TreeMap<>();
						if(perHour.containsKey(hour)) {
							perHour.put(hour, perHour.get(hour) + 1);
						}
						else {
							perHour.put(poi.getTimeIn().getHour(), 1);
						}
						visitorsPerRoomPerHour.put(r, perHour);
					}
					else {
						TreeMap<Integer,Integer> perHour = visitorsPerRoomPerHour.get(r);
						if(perHour.containsKey(hour))
							perHour.put(hour, perHour.get(hour) + 1);	
						else {
							perHour.put(hour, 1);
						}
						visitorsPerRoomPerHour.put(r, perHour);

					}
				}
			}
		}

		return visitorsPerRoomPerHour;	
	}
	
	public HashMap<Long, Float> attractionPowerStats(){
		HashMap<Long, Float> attractionPower = new HashMap<>();
		List<Visit> visits = (List<Visit>)this.visitRepository.findAll();
		float size = visits.size();
		for(Visit v: visits) {
			HashMap<Long,LocalTime> poiV = this.visitService.getStatsPoiVisitor(v);
			for(Long id: poiV.keySet()) {
				if(attractionPower.containsKey(id)) {
					float temp = attractionPower.get(id);
					temp = (temp/100)*size;
					attractionPower.put(id, ((temp + 1)/size)*100);
				}
				else {
					attractionPower.put(id, (1/size)*100);
				}
			}
		}
		
		return attractionPower;
	}
	
	public HashMap<Long, LocalTime> holdingPowerStats(){
		HashMap<Long, LocalTime> holdingPower = new HashMap<>();
		List<Visit> visits = (List<Visit>)this.visitRepository.findAll();
		for(Visit v: visits) {
			HashMap<Long,LocalTime> poiV = this.visitService.getStatsPoiVisitor(v);
			for(Long id: poiV.keySet()) {
				if(holdingPower.containsKey(id)) {
					LocalTime temp = holdingPower.get(id);
					temp = temp.plusNanos(poiV.get(id).toNanoOfDay());
					holdingPower.put(id, temp);
				}
				else {
					holdingPower.put(id, poiV.get(id));
				}
			}
			
		}
		return holdingPower;
	}
}
