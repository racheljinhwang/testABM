package abmt2023.project.mode_choice;

import org.eqasim.switzerland.mode_choice.parameters.SwissCostParameters;

public class DrtCostParameters extends SwissCostParameters {

	public double carCost_CHF_km = 0.0;

	public double ptCost_CHF_km = 0.0;
	public double ptMinimumCost_CHF = 0.0;

	public double ptRegionalRadius_km = 0.0;
	//TODO: add your own cost parameters
	public static DrtCostParameters buildDefault() {
		DrtCostParameters parameters = new DrtCostParameters();

		parameters.carCost_CHF_km = 0.26;

		parameters.ptCost_CHF_km = 0.6;
		parameters.ptMinimumCost_CHF = 2.7;

		parameters.ptRegionalRadius_km = 15.0;

		return parameters;
	}
}
