package abmt2023.week7.lectures.injection;

import com.google.inject.AbstractModule;

import abmt2023.week7.lectures.Link;

public class LinkModule extends AbstractModule {

	protected void configure() {
		// 1. in this case Link object needs to have a constructor
		// annotated with @Inject
		// if the consturctor has dependencies, then those
		// need to be provided to the Guice framework
		// if these dependencies are not provided the Guice framework
		// will create a default value (i.e., for String object it will create an empty String)
		
		// if we do not bind a Link object here, when Network object is created it will
		// still create a Link object that contains an empty String for the name variable
		// by doing this ourselves we ensure control over which Link object is used in the application
		
		// asEagerSingleton() ensure that only one Link object is created and
		// used throughout the project
		bind(Link.class).asEagerSingleton();

		// 2. here we do not need an @Inject annotation in Link
		// as we are directly binding an instance of the object

		// bind(Link.class).toInstance(new Link("Wehntalerstrasse"));
		
		// 3. option
		//String name = new String("Wehntalerstrasse");

		//bind(String.class).annotatedWith(Names.named("StreetName")).toInstance(name);

		// in this case we need LinkProvider constructor to have Inject annotation
		// bind(Link.class).toProvider(LinkProvider.class);
		
		// in this case we do not need an Inject annotation in the LinkProvider constructor
		//bind(Link.class).toProvider(new LinkProvider("Wehntalerstrasse"));

	}
}