package abmt2023.week7.exercises;

import com.google.inject.Inject;

public class FleetOperator {

	private final Dispatcher dispatcher;
	
	@Inject
	public FleetOperator(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	
	public void operateFleet() {
		// ....
		this.dispatcher.dispatch();
		
		// ....
	}
}
