package abmt2023.project.mode_choice.estimators;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.utilities.UtilityEstimator;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.CarPredictor;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Inject;

import abmt2023.project.mode_choice.AstraModeParameters;
import abmt2023.project.mode_choice.predictors.AstraPersonPredictor;
import abmt2023.project.mode_choice.predictors.AstraTripPredictor;
import abmt2023.project.mode_choice.predictors.DRTPredictor;

public class DRTUtilityEstimator implements UtilityEstimator {

	private final AstraModeParameters parameters; //TODO: do not forget to add appropriate parameters to this class
	private final AstraPersonPredictor personPredictor;
	private final AstraTripPredictor tripPredictor;
	private final DRTPredictor drtpredictor;
	
	@Inject
	public DRTUtilityEstimator(AstraModeParameters parameters, DRTPredictor drtpredictor,
			AstraPersonPredictor personPredictor, AstraTripPredictor tripPredictor) {
		

		this.parameters = parameters;
		this.personPredictor = personPredictor;
		this.tripPredictor = tripPredictor;
		this.drtpredictor = drtpredictor;
	}
	
	
	@Override
	public double estimateUtility(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		// TODO calculate the utility of this trip
		
		return 0;
	}

}
