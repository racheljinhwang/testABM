package abmt2023.week7.exercises.matsim;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;

import com.google.inject.name.Names;

import abmt2023.week4.exercises.counter.MyEventHandler;
import abmt2023.week7.lectures.Link;
import abmt2023.week7.lectures.Network;
import abmt2023.week7.lectures.injection.LinkProvider;

public class RunMatsimInjection {

	public static void main(String[] args) {

		// MATSim context

		// first we load the config, you need to provide a path the the equil scenario
		// on your computer
		Config config = ConfigUtils.loadConfig(args[0]);
		// we only want to simulate one iteration to see if our code works
		config.controler().setLastIteration(1);
		// as we are testing our code we can always delete the output directory before
		// starting the simulation
		config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
		// we now create the Controler object
		Controler controler = new Controler(config);

		// MATSim uses an addOverridingModule() method in the controler
		// to set-up Guice dependency injections
		// this is a standard synthax that should be always used
		controler.addOverridingModule(new AbstractModule() {

			// one method should be implemented and that is install()
			// here we can set-up our bindings
			// the rest works the same as for the standard Guice injection
			@Override
			public void install() {
				// we want to create a String object that should be passed
				// to the injection framework
				String name = new String("X");
				bind(String.class).annotatedWith(Names.named("StreetName")).toInstance(name);
				// we want to provide a Link object using a provider
				bind(Link.class).toProvider(LinkProvider.class);
				// we want to bind a NetworkGuice object and ensure that only one instance is
				// created
				bind(Network.class).asEagerSingleton();

			}
		});

		// we can add as many AbstractModule(s) as we want
		controler.addOverridingModule(new AbstractModule() {

			@Override
			public void install() {

				// in this way we tell MATSim that it should pass events to this event handler
				
				// Option 1
				// this.addEventHandlerBinding().to(MyEventHandler.class);
				// if we do not bind this as a Singleton MATSim will create two instances of
				// this class
				// first time when it is added as EventHandler in MATSim
				// and second time when it is needed in the MyControlerListener
				// the second one will not be registered as an EventHandler in MATSim and
				// event will not be passed to it
				// bind(MyEventHandler.class).asEagerSingleton();

				// as an alternative we can provide an EventHandler using a Provider
				// we should also ensure it is provided as a singleton
				// if we use this solution lines 63 and 70 should be commented out
				
				// Option 2
				// this.addEventHandlerBinding().to(MyEventHandler.class);
				// bind(MyEventHandler.class).toProvider(MyEventHandlerProvider.class).asEagerSingleton();

				// now we also need to tell MATSim that it should use our MyControlerListener
				// this.addControlerListenerBinding().to(MyControlerListenerInjectionV1.class);

				// another alternative would be the following (comment out all lines above)
				// bind the MyEventHandler either as a class or with a provider
				bind(MyEventHandler.class).toProvider(MyEventHandlerProvider.class);
				// then just add a controler listener
				// what is here actually different can be seen within the listener itself
				this.addControlerListenerBinding().to(MyControlerListenerInjectionV2.class);

			}
		});

		controler.run();
		// similarly like for default Guice we can obtain instances of different objects
		// that we injected using the Guice framework into MATSim
		Network netM = controler.getInjector().getInstance(Network.class);
		System.out.println(netM.getLink().getName());

	}

}