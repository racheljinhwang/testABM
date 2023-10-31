package abmt2023.week7.lectures.injection;

import com.google.inject.Provider;

import abmt2023.week7.lectures.Link;

public class LinkProvider implements Provider<Link> {

	private final String name;

	public LinkProvider(String name) {

		this.name = name;
	}

	// this method will be called when the program needs a Link object
	// it allows to return different instances of the Link object
	// depending on your configuration
	@Override
	public Link get() {

		if (name.equals("Wehntalerstrasse")) {
			return new Link("Zurich city link");
		}

		else
			return new Link("Some other road; not Wehntalerstrasse");

	}

}
