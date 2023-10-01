package abmt2023.week3.exercises;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.router.TripStructureUtils.Trip;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.io.IOUtils;


public class IOUtilsExtractTrips {
    public static void main(String[] args) throws IOException {

        //Create Scenario that would be used to create a population container to read our plans into
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());


        //Read the population file of interest into the population container
        PopulationReader popReader = new PopulationReader(scenario);
        popReader.readFile(args[0]);
        
        Population population = scenario.getPopulation();

        //Because we want to write to CSV we would use the generic Bufferred writer
        BufferedWriter writer = IOUtils.getBufferedWriter("output_trips.csv");

        //we would open the writer to begin writing
        //first specify the headers
        writer.write("person_id;origin;destination;mode\n");

        //To get the information we need, first have to go through all the persons in the population file and
    	 for (Person person: population.getPersons().values() ){

             //Then we want to get the trips of the selected plan of the person if it is an output file we are reading
         	List<Trip> trips = TripStructureUtils.getTrips(person.getSelectedPlan());
             
         	String origin = "";
             String destination = "";
             String mode ="";

             //We want to go through all the trips (Legs) of each person using the TripsStructureUtils class

             for(TripStructureUtils.Trip trip: trips){
                 origin = trip.getOriginActivity().getType();
                 destination = trip.getDestinationActivity().getType();

                 //To get the mode in this case we have only one leg element hence we get the first leg element with get(0)
                 mode = trip.getLegsOnly().get(0).getMode();

                 //Write out activities for each person
                 writer.write(person.getId().toString() + ";"
                         + origin + ";"
                         + destination + ";"
                         + mode + "\n"
                     );
                 }



             }

             writer.flush();
             writer.close();
             
             System.out.println("Finished!");



         }
}