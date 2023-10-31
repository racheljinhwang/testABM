package abmt2023.week7.exercises;

import com.google.inject.Inject;

public class FastDispatcher implements Dispatcher {
	

	@Inject
	public FastDispatcher() {
		
	}
	
	

	@Override
	public void dispatch() {
		// do something that is really fast here
		System.out.println("we are doing fast dispatching!");
	}

}
