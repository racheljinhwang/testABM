package abmt2023.week2.exercises;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;


public class RunMatsimExample {

	public static void main(String[] args) {
		
		Config config = ConfigUtils.loadConfig(args[0]);
		
		Controler controler = new Controler(config);
		
		controler.run();

	}

}