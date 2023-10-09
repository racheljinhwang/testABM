package abmt2023.week4.exercises.average_time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

public class CalculateAverageTravelTime implements PersonDepartureEventHandler, PersonArrivalEventHandler{
	
	//you will need a HashMap<>() to store information

	//get the departure time for each person
	@Override
	public void handleEvent(PersonDepartureEvent event) {
		
		
	}

	//get the arrival time and calculate the duration
	@Override
	public void handleEvent(PersonArrivalEvent event) {
		
		 
	}
	
	//return travel time
	public double getAverageTravelTime() {
		return 0.0;
		}

	//return travel time
	public Map<String, Double> getAverageTravelTimePerMode() {
		
		return null;
		}
	



}
