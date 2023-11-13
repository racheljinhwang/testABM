package abmt2023.week9.lectures.drt;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contrib.drt.routing.DrtRoute;
import org.matsim.contrib.drt.routing.DrtRouteFactory;
import org.matsim.contrib.drt.run.MultiModeDrtConfigGroup;
import org.matsim.contrib.drt.run.MultiModeDrtModule;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.contrib.dvrp.run.DvrpModule;
import org.matsim.contrib.dvrp.run.DvrpQSimComponents;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class RunDrt {

	 public static void main (String[] args) {
		  
		//Scenario: Siouxfalls example project
		    	
		        //Load the config object
		        String configPath = args[0]; 
		        Config config = ConfigUtils.loadConfig(configPath);
		        
		        // add drt and dvrp config groups
		     // Configure DVRP and DRT module
		        DvrpConfigGroup dvrpConfig = new DvrpConfigGroup();
		        config.addModule(dvrpConfig);

		        MultiModeDrtConfigGroup multiModeDrtConfig = new MultiModeDrtConfigGroup();
		        config.addModule(multiModeDrtConfig);
		                
		        config.controler().setOutputDirectory("outputTest6");
		        config.controler().setLastIteration(0);
		        
		        
		   
		        //Load the scenario object

		        Scenario scenario = ScenarioUtils.loadScenario(config);
		        
//		        {
//		        	//begin code block
//		        	
//		        	//modify population - set drt mode and update routeMode - No outside trips allowed to use DRT
//			        for (Person person : scenario.getPopulation().getPersons().values()) {
//			            for (final Plan plan : person.getPlans()) {
//			                for (final TripStructureUtils.Trip trip : TripStructureUtils.getTrips(plan)) {
//			                    for (final PlanElement pe : trip.getTripElements()) {
//			                        if (pe instanceof Leg) {
//			                            //set all car trips to drt trips
//			                            
//		                                if (((Leg) pe).getMode().equals("car")) {
//		                                    ((Leg) pe).setMode("drt");
//		                                    pe.getAttributes().putAttribute("routingMode", "drt");
//		                                }
//			                            
//			                        }
//			                    }
//			                }
//			            }
//			        }
//		        
//		        //end code block
//		        }
		        
		        
		        // Add DRT route factory
		            scenario.getPopulation().getFactory().getRouteFactories().setRouteFactory(DrtRoute.class,
		                    new DrtRouteFactory());
		        
		             
		      //Create the controller object
		        Controler controler = new Controler(scenario);
		        
		        controler.addOverridingModule(new DvrpModule());
		        controler.addOverridingModule(new MultiModeDrtModule());
		        controler.configureQSimComponents(components -> {
		            DvrpQSimComponents.activateAllModes(multiModeDrtConfig).configure(components);
		        });
		        
		        controler.run();
		    }
}
