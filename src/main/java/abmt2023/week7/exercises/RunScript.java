package abmt2023.week7.exercises;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RunScript {

	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new DispatchingModule(args[0]));

		Dispatcher dispatcher = injector.getInstance(Dispatcher.class);

		if (dispatcher instanceof FastDispatcher) {
			System.out.println("We are using a fast dispatcher");
		} else if (dispatcher instanceof SmartDispatcher) {
			System.out.println("We are using a smart dispatcher");
		} else {
			System.out.println("We are using an unknown dispatcher; this should not happen");
		}

	}

}
