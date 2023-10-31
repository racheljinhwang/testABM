package abmt2023.week7.exercises;

import com.google.inject.Inject;

public class SmartDispatcher implements Dispatcher {

	@Inject
	public SmartDispatcher() {
	}

	@Override
	public void dispatch() {
		// do something really smart here
		// but necessarily fast

		System.out.println("We are doing something smart!");

	}

}
