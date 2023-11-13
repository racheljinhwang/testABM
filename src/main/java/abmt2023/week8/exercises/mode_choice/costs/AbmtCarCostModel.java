package abmt2023.week8.exercises.mode_choice.costs;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.cost.AbstractCostModel;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Inject;

import abmt2023.week8.exercises.mode_choice.parameters.AbmtCostParameters;

public class AbmtCarCostModel extends AbstractCostModel {

    private final AbmtCostParameters costParameters;

  //Rather than hard code the car cost as the commented code below, it is better to make it flexible for change outside of the code. This is what we have done by creating the cost parameters
    //double carCost_CHF_km = 0.25;
    
    //adding @Inject is necessary to have access to the costParameters or this constructor would not be recognized as a suitable constructor
    @Inject
    public AbmtCarCostModel(AbmtCostParameters costParameters) {
        super("car");
        this.costParameters = costParameters;
    }
    

    //this is a method defined in the super class we extend and we override that of the superclass and modify to suit our need
	@Override
	public double calculateCost_MU(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		return costParameters.carCost_USD_km * getInVehicleDistance_km(elements);
	}


}
