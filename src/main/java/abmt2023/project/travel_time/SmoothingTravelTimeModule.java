package abmt2023.project.travel_time;

import org.matsim.api.core.v01.network.Network;
import org.matsim.core.config.groups.TravelTimeCalculatorConfigGroup;
import org.matsim.core.controler.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import abmt2023.project.config.AstraConfigGroup;

public class SmoothingTravelTimeModule extends AbstractModule {
	@Override
	public void install() {
		addEventHandlerBinding().to(SmoothingTravelTime.class);
		addControlerListenerBinding().to(SmoothingTravelTime.class);
		addTravelTimeBinding("car").to(SmoothingTravelTime.class);
	}

	@Provides
	@Singleton
	public SmoothingTravelTime provideSmoothingTravelTime(Network network, TravelTimeCalculatorConfigGroup config,
			AstraConfigGroup astraConfig) {
		double startTime = 0.0;
		double endTime = config.getMaxTime();
		double interval = config.getTraveltimeBinSize();

		boolean fixFreespeedTravelTime = true;

		double smoothingIncreasingAlpha = astraConfig.getTravelTimeEstimationAlpha(); // 9;
		double smoothingDecreasingAlpha = astraConfig.getTravelTimeEstimationAlpha();

		return new SmoothingTravelTime(startTime, endTime, interval, smoothingIncreasingAlpha, smoothingDecreasingAlpha,
				fixFreespeedTravelTime, network);
	}
}