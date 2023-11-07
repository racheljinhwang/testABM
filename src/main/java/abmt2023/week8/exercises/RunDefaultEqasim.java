package abmt2023.week8.exercises;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.mode_choice.EqasimModeChoiceModule;
import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;
import org.matsim.api.core.v01.Scenario;
import org.matsim.contribs.discrete_mode_choice.modules.DiscreteModeChoiceModule;
import org.matsim.contribs.discrete_mode_choice.modules.config.DiscreteModeChoiceConfigGroup;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

public class RunDefaultEqasim {
	
	public static void main(String[] args)  {
		
		String configPath = args[0]; //use the sioux fall scenario config_default_eqasim.xml file
		
		//load the config file with the necessary modules (DMC module and eqasim...)
		//Hint: Search for the EqasimConfigurator class and see what class we should load
		 //First load the Siouxfalls scenario
		
        Config config = ConfigUtils.loadConfig(configPath, new EqasimConfigGroup(), new DiscreteModeChoiceConfigGroup());
        

        config.controler().setLastIteration(2);
        
        
        //config.controler().setOutputDirectory("output3110");
        config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);


		//load scenario
        Scenario scenario = ScenarioUtils.loadScenario(config);
        
      //Now that we have chosen eqasimUtility estimator for setting up the dmc we need to identify for each mode what estimators we want to use
        EqasimConfigGroup eqasimConfig = (EqasimConfigGroup) config.getModules().get(EqasimConfigGroup.GROUP_NAME);

        //these are the default utility estimators already prepared for the different modes in the eqasim framework. this can also be specified in the config file
        eqasimConfig.setEstimator("walk", "WalkUtilityEstimator");
        eqasimConfig.setEstimator("bike", "BikeUtilityEstimator");
        eqasimConfig.setEstimator("pt", "PtUtilityEstimator");
        eqasimConfig.setEstimator("car", "CarUtilityEstimator");
        
      //we also need to specify the cost model --Currently we use the default cost model provided in the eqasim framework
        //the ZeroCostModel class can be searched for and there you will see that it returns zero as the cost of using these modes
        //we would need to later define our own cost models
        eqasimConfig.setCostModel("car", "ZeroCostModel");
        eqasimConfig.setCostModel("pt", "ZeroCostModel");

		//create controler
		
        Controler controler = new Controler(scenario);

        
		//add modules to controler
        controler.addOverridingModule(new EqasimModeChoiceModule());
        controler.addOverridingModule(new DiscreteModeChoiceModule());
        
      //Add an injection for mode parameters for the code to work
		
		 controler.addOverridingModule(new AbstractModule() {
		 
		 @Override public void install() {
		 bind(ModeParameters.class).asEagerSingleton(); } });
		 
        
      //for clean code and organization, we create a module for all our mode choice class injections and add overriding module as below
        //controler.addOverridingModule(new AbmtModeChoiceModule());

        controler.run();

		
		
	}

}
