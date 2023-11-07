package abmt2023.week8.exercises.mode_choice.parameters;

import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;

public class AbmtModeParameters extends ModeParameters {

    //Define the custom parameter that we need in the car utility estimator. To do that we create an inner class object for this and add the parameter we need as a field
    public class AbmtCarParameters {
        public double alpha_male = 0.0;
    }

    //we then need to create an object of the AbmtCarParameters which will be injected for use in the utility estimator (this means this class needs to be binded)
    public final AbmtCarParameters abmtCar = new AbmtCarParameters();

    //If we want to change all the mode parameters manually by code then we would have to define all of them in a buildDefault method that returns the parameter terms as below and change them (not recommended)
    //For this to work this method has to be called and injected by Guice using a provider

    public static AbmtModeParameters buildDefault() {
        AbmtModeParameters parameters = new AbmtModeParameters();
        //car - you can define all the ones you want to change including those already defined by eqasim that we extend here
        parameters.car.alpha_u = 0.0;
        parameters.abmtCar.alpha_male = 0.0;
        //...
        return parameters;
    }

}
