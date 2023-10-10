package abmt2023.week3.exercises;


import java.io.BufferedWriter;
import java.io.IOException;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.io.IOUtils;


public class IOUtilsExtractActivities {
    public static void main(String[] args) throws IOException {

        //Class activity
    	
    	Config config = ConfigUtils.createConfig();
    	
    	//First create Scenario
        Scenario scenario = ScenarioUtils.createMutableScenario(config);
        Population population = scenario.getPopulation();

      //Read the population file of interest into the population container
        PopulationReader popReader = new PopulationReader(scenario);
        popReader.readFile(args[0]);
       

        //Because we want to write to CSV we would use the generic bufferred writer and specify the csv file name
        BufferedWriter writer = IOUtils.getBufferedWriter("activities.csv");

        //we would open the writer to begin writing
        //first specify the headers
        writer.write("person_id;age;sex;activity;x;y\n");

      //To get the information we need, first have to go through all the persons in the population file and
        for (Person person: population.getPersons().values() ){

            //Initialize the variables we need

            String activity_type = "";
            double x = 0.0;
            double y = 0.0;

            //Then we need to go through the plans to get the activities for each person.
            // First you want the selected plan of the person, if it is an output file we are reading
            //Then to access the individual contents of the plans, these are the plan elements,
            // the plan has two instances - activity and leg

            for (PlanElement element: person.getSelectedPlan().getPlanElements()){
                //check for the instance we need - activity
                if(element instanceof Activity) {
                    Activity activity = (Activity) element;
                    activity_type = activity.getType();
                    x = activity.getCoord().getX();
                    y = activity.getCoord().getY();

                    //Write out activities for each person
                    writer.write(person.getId().toString() + ";"
                            + person.getAttributes().getAttribute("age") + ";"
                            + person.getAttributes().getAttribute("sex") + ";"
                            + activity_type + ";"
                            + x + ";"
                            + y + "\n"
                    );

                }

            }



        }

        //we have to close the writer
        writer.flush();
        writer.close();



    }
}

