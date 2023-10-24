package abmt2023.project.mode_choice;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.analysis.EqasimAnalysisModule;
import org.eqasim.core.simulation.mode_choice.EqasimModeChoiceModule;
import org.eqasim.switzerland.SwitzerlandConfigurator;
import org.eqasim.switzerland.mode_choice.SwissModeChoiceModule;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.CommandLine.ConfigurationException;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

import abmt2023.project.config.AstraConfigurator;
import abmt2023.project.travel_time.SmoothingTravelTimeModule;

public class RunBaselineSimulation {
	
	/**
	 * 
	 * you need one argument to run this class
	 * --config-path "path-to-your-config-file/config.xml"
	 * 
	 */
	static public void main(String[] args) throws ConfigurationException, MalformedURLException, IOException {
		// Some paramters added from AdPT
		
		
		CommandLine cmd = new CommandLine.Builder(args) //
				.requireOptions("config-path") //
				.allowPrefixes( "mode-parameter", "cost-parameter") //
				.build();

		Config config = ConfigUtils.loadConfig(cmd.getOptionStrict("config-path"), AstraConfigurator.getConfigGroups());
		AstraConfigurator.configure(config);
		cmd.applyConfiguration(config);
		
		Scenario scenario = ScenarioUtils.createScenario(config);

		SwitzerlandConfigurator.configureScenario(scenario);
		ScenarioUtils.loadScenario(scenario);
		SwitzerlandConfigurator.adjustScenario(scenario);
		AstraConfigurator.adjustScenario(scenario);

		EqasimConfigGroup eqasimConfig = EqasimConfigGroup.get(config);

		for (Link link : scenario.getNetwork().getLinks().values()) {
			double maximumSpeed = link.getFreespeed();
			boolean isMajor = true;

			for (Link other : link.getToNode().getInLinks().values()) {
				if (other.getCapacity() >= link.getCapacity()) {
					isMajor = false;
				}
			}

			if (!isMajor && link.getToNode().getInLinks().size() > 1) {
				double travelTime = link.getLength() / maximumSpeed;
				travelTime += eqasimConfig.getCrossingPenalty();
				link.setFreespeed(link.getLength() / travelTime);
			}
		}

		// EqasimLinkSpeedCalcilator deactivated!

		Controler controller = new Controler(scenario);
		SwitzerlandConfigurator.configureController(controller);
		controller.addOverridingModule(new EqasimAnalysisModule());
		controller.addOverridingModule(new EqasimModeChoiceModule());
		controller.addOverridingModule(new SwissModeChoiceModule(cmd));
		controller.addOverridingModule(new AstraModule(cmd));

		AstraConfigurator.configureController(controller, cmd);

		controller.addOverridingModule(new SmoothingTravelTimeModule());

		controller.run();
	}
}