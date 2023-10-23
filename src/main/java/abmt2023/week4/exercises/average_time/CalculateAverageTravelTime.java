package abmt2023.week4.exercises.average_time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

public class CalculateAverageTravelTime implements PersonDepartureEventHandler, PersonArrivalEventHandler {
	private final Map<Id<Person>, Double> times = new HashMap<>();
	private final Map<String, ArrayList<Object>> timesByMode = new HashMap<>(); //first and last list elements are counts and traveltime sum
	private double travelTimeSum = 0.0;
	private int travelTimeCount = 0;

	//get the departure time for each person
	@Override
	public void handleEvent(PersonDepartureEvent event) {
		this.times.put(event.getPersonId(), event.getTime());
		
	}

	//get the arrival time and calculate the duration
	@Override
	public void handleEvent(PersonArrivalEvent event) {
		double depTime = this.times.get(event.getPersonId());
		double travelTime = event.getTime() - depTime;
		String mode = event.getLegMode();
		
		if(timesByMode.containsKey(mode)) {
			ArrayList<Object> tmp = timesByMode.get(mode);
			timesByMode.get(mode).set(0, (int)tmp.get(0) + 1);
			timesByMode.get(mode).set(1, (double)tmp.get(1) + travelTime);
			//System.out.println(timesByMode.get(mode));
		} else {
			ArrayList<Object> traveltimeList = new ArrayList<Object>() {{
	            add(1);
	            add(travelTime);
	        }};
			timesByMode.put(mode, traveltimeList);
		} 
		this.travelTimeSum += travelTime;
		this.travelTimeCount++;
		 
	}
	
	//return travel time
	public double getAverageTravelTime() {
		return this.travelTimeSum / this.travelTimeCount;
		}

	//return travel time
	public Map<String, Double> getAverageTravelTimePerMode() {
		Map<String, Double> avgTimePerMode = new HashMap<>();
		for (String mode : timesByMode.keySet()) {
			double avgTime = (double)timesByMode.get(mode).get(1)/(int)timesByMode.get(mode).get(0);
			avgTimePerMode.put(mode, avgTime);
		}
		return avgTimePerMode;
		}



}
