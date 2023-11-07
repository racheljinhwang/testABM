package abmt2023.week8.exercises.mode_choice.parameters;

import org.eqasim.core.simulation.mode_choice.ParameterDefinition;

public class AbmtCostParameters implements ParameterDefinition {
    //Define the cost parameters that would be needed in the cost model for each mode
    //Later this class will need to be binded so it is injected into the cost model
    public double carCost_USD_km; //the cost can be named anything, we assume that its in USD per km, it all depends on how you use it in the cost model
    public double ptCost_USD;
    
    //not compulsory if you want to insist on providing the cost externally
    public static AbmtCostParameters buildDefault() {

    	AbmtCostParameters parameters = new AbmtCostParameters();

        parameters.carCost_USD_km = 0.55;
        parameters.ptCost_USD = 5;

        return parameters;
    }

}
