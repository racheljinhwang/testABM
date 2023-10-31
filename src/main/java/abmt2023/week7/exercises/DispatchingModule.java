package abmt2023.week7.exercises;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class DispatchingModule extends AbstractModule {

	private final String dispatcherType;

	public DispatchingModule(String dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	protected void configure() {

		bind(String.class).annotatedWith(Names.named("dispatcherType")).toInstance(this.dispatcherType);

		bind(Dispatcher.class).toProvider(DispatcherProvider.class);

	}

}
