package abmt2023.week4.exercises.counter;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventsPostprocessing {

	public static void main(String[] args) {

		EventsManager eventsManager = EventsUtils.createEventsManager();
		
		// create an object for the EventHandler and add to the event manager
		MyEventHandler myEventHandler = new MyEventHandler();
		eventsManager.addHandler(myEventHandler);
		
		// read the events from our events file
		MatsimEventsReader matsimEventsReader = new MatsimEventsReader(eventsManager);
		
		// the events manager will automatically process the read events 
		matsimEventsReader.readFile(args[0]);
		
		System.out.println(myEventHandler.getCounterEnter());
		System.out.println(myEventHandler.getCounterLeave());
				
	}

}
