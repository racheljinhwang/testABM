package abmt2023.week8.exercises.mode_choice;

import java.io.File;
import java.io.IOException;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.mode_choice.AbstractEqasimExtension;
import org.eqasim.core.simulation.mode_choice.ParameterDefinition;
import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import abmt2023.week8.exercises.mode_choice.costs.AbmtCarCostModel;
import abmt2023.week8.exercises.mode_choice.costs.AbmtPtCostModel;
import abmt2023.week8.exercises.mode_choice.estimators.AbmtCarUtilityEstimator;
import abmt2023.week8.exercises.mode_choice.parameters.AbmtCostParameters;
import abmt2023.week8.exercises.mode_choice.parameters.AbmtModeParameters;

public class AbmtModeChoiceModule extends AbstractEqasimExtension {

    static public final String MODE_AVAILABILITY_NAME = "AbmtModeAvailability";
    static public final String CAR_COST_MODEL_NAME = "AbmtCarCostModel";
    static public final String PT_COST_MODEL_NAME = "AbmtPtCostModel";

    @Override
    protected void installEqasimExtension() {
        // bindUtilityEstimator is a method in AbstractEqasimExtension that make possible add
        // to a mapbinder objects of type UtilityEstimator with a specific key, in this case AbmtCarEstimator
         bindUtilityEstimator("AbmtCarUtilityEstimator").to(AbmtCarUtilityEstimator.class);



        //We no longer need to bind the default ModeParameters class but point the DCM module to use the one we defined
        //to use in our utility estimator for car
        //bind(ModeParameters.class).asEagerSingleton();

        bind(ModeParameters.class).to(AbmtModeParameters.class);
        //bind(AbmtModeParameters.class).asEagerSingleton(); //we would need this if we did not bind it as singleton below

        //Here again we add mapbinder objects of type CostModel with a specific key, in this example we use a constant name for code clarity and organization
        bindCostModel(CAR_COST_MODEL_NAME).to(AbmtCarCostModel.class);
        bindCostModel(PT_COST_MODEL_NAME).to(AbmtPtCostModel.class);

        //Here we bind our mode availability class so it is called when it is used
        bindModeAvailability(MODE_AVAILABILITY_NAME).to(AbmtModeAvailability.class);


    }

    //@Provides is used as a factory for a new object. Every time a new object of
    // type AbmtModeParameters is required as parameter in a constructor implementing @Inject
    //this object will be created by using the following method where we can define where the parameters should be gotten from


 @Provides
    @Singleton
    public AbmtModeParameters provideModeChoiceParameters(EqasimConfigGroup config) throws IOException {
        AbmtModeParameters parameters = AbmtModeParameters.buildDefault();
        
        //if we had no build default we can just create an empty template but we must then pass a file with the parameters
        //AbmtModeParameters parameters = new AbmtModeParameters();
        

        //specify that the parameters should be gotten from the config file if there is a file path specified in the eqasim module for mode parameters
         if (config.getModeParametersPath() != null) {
             ParameterDefinition.applyFile(new File(config.getModeParametersPath()), parameters);
         }

        return parameters;
    }



//Bind the cost parameters class using provider as done for mode parameters
@Provides
@Singleton
public AbmtCostParameters provideCostParameters(EqasimConfigGroup config) throws IOException {
    AbmtCostParameters parameters = AbmtCostParameters.buildDefault();

    //specify that the parameters should be gotten from the config file if there is a file path specified in the eqasim module for mode parameters
    if (config.getCostParametersPath() != null) {
        ParameterDefinition.applyFile(new File(config.getCostParametersPath()), parameters);
    }

    return parameters;
}


}
