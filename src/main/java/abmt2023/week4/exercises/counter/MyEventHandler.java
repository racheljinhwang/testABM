package abmt2023.week4.exercises.counter;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;

//we want to analyse link enter and link leave events
public class MyEventHandler implements LinkEnterEventHandler, LinkLeaveEventHandler{

	// we want to have two counters of these events
	private int counterEnter = 0;
	private int counterLeave = 0;

	
	@Override
	public void handleEvent(LinkEnterEvent event) {
		if (event.getTime() > 0.0)
			counterEnter++;
		
	}
	
	@Override
	public void handleEvent(LinkLeaveEvent event) {
		if (event.getTime() > 0.0)
			counterLeave++;
		
	}
	
	@Override
	public void reset(int iteration) {
		this.counterEnter = 0;
		this.counterLeave = 0;
	}
	
	public int getCounterEnter() {
		return counterEnter;
	}

	public int getCounterLeave() {
		return counterLeave;
	}

}
