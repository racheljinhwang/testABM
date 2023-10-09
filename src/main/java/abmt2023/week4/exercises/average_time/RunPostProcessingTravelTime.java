package abmt2023.week4.exercises.average_time;

import java.util.Map.Entry;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunPostProcessingTravelTime {

	public static void main(String[] args) {
		
		EventsManager eventsManager = EventsUtils.createEventsManager();
		// create an object for the EventHandler and add to the event manager
		CalculateAverageTravelTime myEventHandler = new CalculateAverageTravelTime();
				eventsManager.addHandler(myEventHandler);
				
		// read the events from our events file
		MatsimEventsReader matsimEventsReader = new MatsimEventsReader(eventsManager);
		
		// the events manager will automatically process the read events 
		matsimEventsReader.readFile(args[0]);
		
		System.out.println(myEventHandler.getAverageTravelTime());
		
		for(Entry<String, Double> entry : myEventHandler.getAverageTravelTimePerMode().entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		
				
	}

}
