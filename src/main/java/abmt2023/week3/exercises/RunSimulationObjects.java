package abmt2023.week3.exercises;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;


public class RunSimulationObjects {
    public static void main (String[] args) {
  
//Scenario: Siouxfalls example project
    	
        //Load the config object
        String configPath = args[0]; //you can also directly add the config path here
        Config config = ConfigUtils.loadConfig(configPath);
        
        //We can access and change the config file directly here, change the iteration number or even the output directory
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
        System.out.println("the old last iteration setting is: " + config.controler().getLastIteration());
        
        config.controler().setLastIteration(1);
        
        System.out.println("the new last iteration setting is: " + config.controler().getLastIteration());
        
        /*
        config.controler().setOutputDirectory("output3");

        //ToDo: how can we change the flow capacity?
        
        //ToDo: Load the scenario object

        Scenario scenario = ;
        
        
        //Let's access modify input files that make up the scenario
        
        
      //Create the controller object
        Controler controler = new Controler(scenario);
        
      //We can modify the config even after loading it in the scenario by calling it through the controler
        //it depends on where you are. You would not want to modify the config using the previously created config object
        //now because it has already been loaded into the scenario
        
        controler.getConfig().controler().setLastIteration(1);

        controler.run();*/
    }
}
