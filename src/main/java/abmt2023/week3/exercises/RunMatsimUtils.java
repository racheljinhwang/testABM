package abmt2023.week3.exercises;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordUtils;


public class RunMatsimUtils {
    public static void main(String[] args){

        /*
        * We would modify the input plan files using some MATSim utils. We create a new agent and add this new agent to the Population data
        * The steps include:
        * 1 - Use the Person class to create a person
        * 2 - Add person attributes and a travel plan to this person
        * 3 - Get the Population data from the scenario
        * 4 - Add the new agent to the population file
        * 5 - Run scenario
        * 6 - view the output plan file to see the new agent
        * */

        //Aim: Creating a person and then adding the person to the population file

        //First load the Siouxfalls scenario
        String configPath = args[0];
        Config config = ConfigUtils.loadConfig(configPath);
        config.controler().setOutputDirectory("output4");

        Scenario scenario = ScenarioUtils.loadScenario(config);

        //Get the population from the scenario
        Population population = scenario.getPopulation();

        //Use population factory to create a person
        PopulationFactory popFactory = population.getFactory();

        //To create person we need the person ID, create Id of type person
        Id<Person> personId = Id.create("22813_3", Person.class);

        //Create person
        Person person = popFactory.createPerson(personId);

        //A person needs a plan so we create a plan container to take the activities and legs of a person
        Plan plan = popFactory.createPlan();
        
        //what are the things needed in a plans file?

        //Create person activities
        //There are many ways to create activity, either from facility id, coordinates, or network link

        //First create Coordinate object for the first activity, ensure the coordinates are within the simulated region
        Coord coord1 = CoordUtils.createCoord(682581.7708999999,4824143.6043);
        
        //Create activities
         Activity act1 = popFactory.createActivityFromCoord("home", coord1);
         act1.setEndTime(6*3600);
         
         Activity act2 = popFactory.createActivityFromCoord("work", CoordUtils.createCoord(683357.0, 4819589.8));
         act2.setEndTime(7*3600);
         
         //coordinates can also be created using the Coord class as below        
         Activity act3 = popFactory.createActivityFromCoord("home", new Coord(682581.7708999999,4824143.6043));

        //Create person leg
         Leg leg1 = popFactory.createLeg("car");
         
         //RouteFactories routeFactories = popFactory.getRouteFactories();
        // routeFactories.createRoute(new Route, null, null);

        //Add activities and leg to plan
         plan.addActivity(act1);
         plan.addLeg(leg1);
         plan.addActivity(act2);
         plan.addLeg(leg1);
         plan.addActivity(act3);

        //Add plan to person
         person.addPlan(plan);

        //We can add some person attributes too. Maybe age, etc
         person.getAttributes().putAttribute("age", 20);
         


        //Add person to population
         population.addPerson(person);


        //Let's run the modified scenario

        Controler controler = new Controler(scenario);


        controler.getConfig().controler().setLastIteration(1);


        controler.run();


    }

}