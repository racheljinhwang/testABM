package abmt2023.project.mode_choice.predictors;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.cost.CostModel;
import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.CachedVariablePredictor;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import abmt2023.project.mode_choice.variables.DRTVariables;

public class DRTPredictor extends CachedVariablePredictor<DRTVariables> {

	private final CostModel costModel;
	private final ModeParameters parameters;
	
	@Inject
	public DRTPredictor(ModeParameters parameters, @Named("drt") CostModel costModel) {
		this.costModel = costModel;
		this.parameters = parameters;
	}
	
	@Override
	protected DRTVariables predict(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		// TODO here you have to calculate all variables associated with the DRT mode;
		// most of these can be obtained from the predicted route; HINT: check other
		// modes
		return null;
	}

}
