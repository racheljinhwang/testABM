package abmt2023.week7.exercises;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class DispatcherProvider implements Provider<Dispatcher> {

	private final String dispatcherType;

	@Inject
	public DispatcherProvider(@Named("dispatcherType") String dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	@Override
	public Dispatcher get() {
		switch (dispatcherType) {
		case "smart":
			return new SmartDispatcher();
		case "fast":
			return new FastDispatcher();
		default:
			throw new RuntimeException("Dispatcher type must be one of the [smart, fast]");
		}
	}

}
