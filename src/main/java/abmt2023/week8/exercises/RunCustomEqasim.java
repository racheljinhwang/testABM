package abmt2023.week8.exercises;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.mode_choice.EqasimModeChoiceModule;
import org.matsim.api.core.v01.Scenario;
import org.matsim.contribs.discrete_mode_choice.modules.DiscreteModeChoiceModule;
import org.matsim.contribs.discrete_mode_choice.modules.config.DiscreteModeChoiceConfigGroup;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

import abmt2023.week8.exercises.mode_choice.AbmtModeChoiceModule;

public class RunCustomEqasim {
	
	public static void main(String[] args)  {
		
		String configPath = args[0]; //use the sioux fall scenario config_default_eqasim.xml file
		
		//load the config file with the necessary modules (DMC module and eqasim...)
		//Hint: Search for the EqasimConfigurator class and see what class we should load
		 //First load the Siouxfalls scenario
		
        Config config = ConfigUtils.loadConfig(configPath, new EqasimConfigGroup(), new DiscreteModeChoiceConfigGroup());
        

        config.controler().setLastIteration(2);
        config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);        
        
        config.controler().setOutputDirectory("output2110_2");


		//load scenario
        Scenario scenario = ScenarioUtils.loadScenario(config);
        
      //Now that we have chosen eqasimUtility estimator for setting up the dmc we need to identify for each mode what estimators we want to use
        EqasimConfigGroup eqasimConfig = (EqasimConfigGroup) config.getModules().get(EqasimConfigGroup.GROUP_NAME);

        //these are the default utility estimators already prepared for the different modes in the eqasim framework. this can also be specified in the config file
        eqasimConfig.setEstimator("walk", "WalkUtilityEstimator");
        eqasimConfig.setEstimator("bike", "BikeUtilityEstimator");
        eqasimConfig.setEstimator("pt", "PtUtilityEstimator");
        
      //ToDo: now we change the estimator for car to the one we defined //we will use the name we will define in the mode choice module
        eqasimConfig.setEstimator("car", "AbmtCarUtilityEstimator");
        
      //ToDo: we also need to specify our own cost model here or in the config
        eqasimConfig.setCostModel("car", "AbmtCarCostModel");
        eqasimConfig.setCostModel("pt", "AbmtPtCostModel");
        
      //to define the mode and cost parameters path directly in the config file. Ensure this file exist

        
      //ToDo: Here is how to add the AbmtModeAvailability to the dcm module directly, one can make changes to any of the parameterset this way
        //First we get the dmc config group
        DiscreteModeChoiceConfigGroup dmcConfig = DiscreteModeChoiceConfigGroup.getOrCreate(config);
        dmcConfig.setModeAvailability("AbmtModeAvailability");
        

		//create controler
		
        Controler controler = new Controler(scenario);

        
		//add modules to controler
        controler.addOverridingModule(new EqasimModeChoiceModule());
        controler.addOverridingModule(new DiscreteModeChoiceModule());
        
      //ToDo: for clean code and organization, we create a module for all our mode choice class injections and add overriding module as below
        controler.addOverridingModule(new AbmtModeChoiceModule());

        controler.run();

		
		
	}

}
