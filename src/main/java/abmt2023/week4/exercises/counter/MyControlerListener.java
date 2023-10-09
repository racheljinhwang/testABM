package abmt2023.week4.exercises.counter;

import java.io.BufferedWriter;
import java.io.IOException;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.IterationEndsListener;
import org.matsim.core.controler.listener.StartupListener;
import org.matsim.core.utils.io.IOUtils;

import com.google.inject.Inject;

public class MyControlerListener implements StartupListener, IterationEndsListener{

	private final MyEventHandler eventHandler = new MyEventHandler();
	private final int[] enterEvents;
	private final int[] leaveEvents;
	private final Scenario scenario;
	
	public MyControlerListener(Scenario scenario) {
		enterEvents = new int[scenario.getConfig().controler().getLastIteration() + 1];
		leaveEvents = new int[scenario.getConfig().controler().getLastIteration() + 1];
		this.scenario = scenario;

	}
	
	public void notifyStartup(StartupEvent event) {
		event.getServices().getEvents().addHandler(this.eventHandler);
	}
	
	public void notifyIterationEnds(IterationEndsEvent event) {
		
		//store event data per iteration
		
		if (event.getIteration() == scenario.getConfig().controler().getLastIteration()) {
			
			// write all data gathered in csv files so specify output path
			String path = "";
			
			try {
				//Because we want to write to CSV we would use the generic bufferred writer and specify the csv file name
		        BufferedWriter writer = IOUtils.getBufferedWriter(path);
				
				//write out the events using the bufferedWriter object
		        
				// flush() tells the Writer to output the stream
				writer.flush();
				
				// it is good practice to close the stream when no more output is expected
				writer.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}			
		}
	}

	public int[] getEnterEvents() {
		return enterEvents;
	}

	public int[] getLeaveEvents() {
		return leaveEvents;
	}

}
