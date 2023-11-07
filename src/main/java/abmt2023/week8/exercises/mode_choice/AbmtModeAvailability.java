package abmt2023.week8.exercises.mode_choice;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.matsim.api.core.v01.population.Person;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import org.matsim.contribs.discrete_mode_choice.model.mode_availability.ModeAvailability;

public class AbmtModeAvailability implements ModeAvailability {

    @Override
    public Collection<String> getAvailableModes(Person person, List<DiscreteModeChoiceTrip> list) {
        //The ModeAvailability class we are implementing requires a collection of modes
        // using the HashSet data structure that extends Collections, we create an empty collection set
        // where we would add modes that a person can have available based on the constraints we define
        Collection<String> modes = new HashSet<>();

        // Modes that are always available to everyone
        modes.add("walk");
        modes.add("pt");
        modes.add("bike");

        //define constraints for using car
        //available only to those within driving age and have car available always
        int age = Integer.parseInt(person.getAttributes().getAttribute("age").toString());
        String carAvail = person.getAttributes().getAttribute("carAvail").toString();
        if(age > 17 && carAvail.equals("always")) {
            modes.add("car");
        }
        return modes;

        //For this code to work, we would need to bind it and for it to be used, we would need to specify that it should be used under the DiscreteModechoice module
        //either through the run script or directly on the config file. Check the config file to know what modeAvailability option is configured there
    }

}
