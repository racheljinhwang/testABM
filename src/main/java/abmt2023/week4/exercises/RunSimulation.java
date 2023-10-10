package abmt2023.week4.exercises;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

import abmt2023.week4.exercises.counter.MyControlerListener;

public class RunSimulation {
    public static void main (String[] args) {
  
//Scenario: Siouxfalls example project
    	
        //Load the config object
        String configPath = args[0]; //you can also directly add the config path here
        Config config = ConfigUtils.loadConfig(configPath);
                
        config.controler().setOutputDirectory("output_counter");
          
        config.controler().setLastIteration(0);
   
        //Load the scenario object

        Scenario scenario = ScenarioUtils.loadScenario(config);
        
             
      //Create the controller object
        Controler controler = new Controler(scenario);
        
        //add controller listener
        //...
        controler.addControlerListener(new MyControlerListener(scenario));
        
        controler.run();
    }
}

