package pl.edu.agh.nnsimulator;

import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.GrossbergLayer;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.KohonenLayer;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;

public class CPNetwork extends NeuralNetwork {
    private KohonenLayer kohonenLayer;
    private GrossbergLayer grossbergLayer;

    public CPNetwork(int inputNum, int kohonenRowsNum, int kohonenColsNum, int outputNum, WeightsInitializer weightsInitializer) throws TooMuchInputLayersException {
        super(inputNum);
        kohonenLayer = new KohonenLayer(inputNum, kohonenRowsNum, kohonenColsNum, weightsInitializer);
        this.addLayer(kohonenLayer);
        grossbergLayer = new GrossbergLayer(ActivationFunctionType.PURELIN, kohonenColsNum*kohonenRowsNum, outputNum, weightsInitializer);
        this.addLayer(grossbergLayer);
    }

    public void setKohonenLearningMode(boolean kohonenLearningMode) {
        kohonenLayer.setLearningMode(kohonenLearningMode);
    }

    public void setGrossbergLearningMode(boolean grossbergLearningMode) {
        grossbergLayer.setLearningMode(grossbergLearningMode);
    }

    public void setExpectedOutput(double[] expectedOutput) throws InvalidDimensionsException {
        grossbergLayer.setExpectedOutput(expectedOutput);
    }

    public void setLearningParameters(LearningParameters learningParameters) {
        kohonenLayer.setLearningParameters(learningParameters);
        grossbergLayer.setLearningParameters(learningParameters);
    }
}
