package abmt2023.week8.exercises.mode_choice.estimators;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;
import org.eqasim.core.simulation.mode_choice.utilities.estimators.CarUtilityEstimator;
import org.eqasim.core.simulation.mode_choice.utilities.estimators.EstimatorUtils;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.CarPredictor;
import org.eqasim.core.simulation.mode_choice.utilities.variables.CarVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Inject;

import abmt2023.week8.exercises.mode_choice.parameters.AbmtCostParameters;
import abmt2023.week8.exercises.mode_choice.parameters.AbmtModeParameters;

public class AbmtCarUtilityEstimator extends CarUtilityEstimator {
	
	private final AbmtModeParameters parameters;
	private final AbmtCostParameters costParameters;
    private final CarPredictor carPredictor;
   
    //Create a constructor that injects the objects we need
    public AbmtCarUtilityEstimator(AbmtModeParameters parameters, AbmtCostParameters costParameters, CarPredictor predictor) {
    	//now we don't care to define our own predictors so we use the default
    	super(parameters, predictor);
		
		this.parameters = parameters;
		this.costParameters = costParameters;
		this.carPredictor = predictor;
	}
	

    //we can create our own method or override the method with ours (if it is the same name)
    //Define our own method to estimate the constant parameters when the person is male to give them preference

    protected double estimateConstantMale() {
        //Here we use the additional alpha value for male from our defined AbmtCarParameter
        return parameters.abmtCar.alpha_male + parameters.car.alpha_u;
    }

    @Override
    protected double estimateMonetaryCostUtility(CarVariables carVariables) {
		return costParameters.carCost_USD_km * EstimatorUtils.interaction(carVariables.euclideanDistance_km,
				parameters.referenceEuclideanDistance_km, parameters.lambdaCostEuclideanDistance) * carVariables.cost_MU;
	}

    //override the estimateUtility method defined in the CarUtilityEstimator class to take into account the constant for Male drivers.
 // we customize it to take into account the constant for Male drivers.
    //Notice how we use other methods (e.g estimateTravelTimeUtility(),..)  from the CarUtilityEstimator class even though we have not written them here.
    // Ctrl+Click on the methods to see what they do

    @Override
	public double estimateUtility(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		CarVariables carVariables = carPredictor.predictVariables(person, trip, elements);

	        double utility = 0.0;

	        //use our constant estimator for male persons
	        if (person.getAttributes().getAttribute("sex").equals("m")) {

	            utility += estimateConstantMale();
	        } else {
	            utility += estimateConstantUtility();
	        }

			utility += estimateAccessEgressTimeUtility(carVariables);
	        utility += estimateTravelTimeUtility(carVariables);
	        utility += estimateMonetaryCostUtility(carVariables);

	        return utility;
	}
}
