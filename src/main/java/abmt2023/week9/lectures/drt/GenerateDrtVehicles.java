package abmt2023.week9.lectures.drt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.contrib.dvrp.fleet.DvrpVehicle;
import org.matsim.contrib.dvrp.fleet.DvrpVehicleSpecification;
import org.matsim.contrib.dvrp.fleet.FleetWriter;
import org.matsim.contrib.dvrp.fleet.ImmutableDvrpVehicleSpecification;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

public class GenerateDrtVehicles {
	
	public static void main(String[] args) throws CommandLine.ConfigurationException, IOException {

        CommandLine cmd = new CommandLine.Builder(args) //
                .requireOptions("networkFile", "outputpath", "fleetSize", "operationStartTime", "operationEndTime", "seats")
                .allowOptions("name_suffix")//
                .build();

        String nameSuffix = cmd.getOption("name_suffix").isPresent() ? cmd.getOption("name_suffix").get() : "drt_vehicles";
        
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        
        Network network = scenario.getNetwork();
        
        
        //read in the input files
        String networkfile = cmd.getOptionStrict("networkFile");
        int numberofVehicles = Integer.parseInt(cmd.getOptionStrict("fleetSize"));
        double operationStartTime = Double.parseDouble(cmd.getOptionStrict("operationStartTime")); //t0
        double operationEndTime = Double.parseDouble(cmd.getOptionStrict("operationEndTime"));	//t1
        int seats = Integer.parseInt(cmd.getOptionStrict("seats"));
        
        String taxisFile = cmd.getOptionStrict("outputpath")+"/"+nameSuffix + "_"+numberofVehicles+"_"+seats+".xml";
        
        List<DvrpVehicleSpecification> vehicles = new ArrayList<>();
        Random random = MatsimRandom.getLocalInstance();
        
        new MatsimNetworkReader(network).readFile(networkfile);
        
        List<Id<Link>> allLinks = new ArrayList<>();
        

        allLinks.addAll(network.getLinks().keySet());

        for (int i = 0; i< numberofVehicles;i++){
            Link startLink;
            do {
            	//randomly select linkID as start link from list of allLinks
            	
                Id<Link> linkId = allLinks.get(random.nextInt(allLinks.size()));
                startLink =  scenario.getNetwork().getLinks().get(linkId);
                
            }
            while (!startLink.getAllowedModes().contains(TransportMode.car));
            
            //for multi-modal networks: Only links where cars can ride should be used.
            vehicles.add(ImmutableDvrpVehicleSpecification.newBuilder().id(Id.create("drt" + i, DvrpVehicle.class))
                    .startLinkId(startLink.getId())
                    .capacity(seats)
                    .serviceBeginTime(operationStartTime)
                    .serviceEndTime(operationEndTime)
                    .build());

        }
        new FleetWriter(vehicles.stream()).write(taxisFile);
        
        System.out.println("Drt vehicle generation completed");
    }

}
