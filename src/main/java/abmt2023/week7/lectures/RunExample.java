package abmt2023.week7.lectures;

import com.google.inject.Guice;
import com.google.inject.Injector;

import abmt2023.week7.lectures.injection.LinkModule;

public class RunExample {

	public static void main(String[] args) {
		// Link link = new Link("Wehntalerstrasse");

		// this is a traditional way of injecting dependencies in java
		// Network network = new Network(link);

		// System.out.println(network.getLink().getName());
		// here we create a Guice framework Injector
		// and configure it with our AbstractModule(s), e.g., LinkModule
		Injector injector = Guice.createInjector(new LinkModule());

		// Think of the Guice framework as a cloud where you store your objects,
		// and you only store objects that you need. Once you need a specific object
		// you search for it in this cloud and if it is not there the Guice framework
		// will attempt to create it.

		// we can ask Guice framework to give us an instance of an Object
		// it will locate the appropriate Provider or constructor with Inject annotation
		// and create this Object. If necessary it will also create other Objects that
		// are
		// injected in the Object we want to create.

		Link linkGuice = injector.getInstance(Link.class);
		Network network = injector.getInstance(Network.class);

		Link linkNetwork = network.getLink();

		System.out.println(linkGuice.getName());
		System.out.println(linkNetwork.getName());
		System.out.println(network.getLink().getName());

	}

}
