package pl.edu.agh.nnsimulator.layers;

import pl.edu.agh.nnsimulator.LearningParameters;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.activationFunctions.TansigActivationFunciton;
import pl.edu.agh.nnsimulator.exceptions.ConnectionNotExistsException;
import pl.edu.agh.nnsimulator.neurons.Neuron;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;

import java.util.Map;

public class GrossbergLayer extends NetworkLayer{
    private double[] expectedOutput;
    private int inputs, outputs;
    private LearningParameters learningParameters;

    private boolean learningMode = false;

    public GrossbergLayer(ActivationFunctionType activationFunctionType, int inputs, int outputs, WeightsInitializer weightsInitializer) {
        super(activationFunctionType, initializeWeights(inputs, outputs, weightsInitializer));
        this.inputs = inputs;
        this.outputs = outputs;
    }

    private static NeuronData[] initializeWeights(int inputs, int outputs, WeightsInitializer weightsInitializer) {
        NeuronData[] neuronsData = new NeuronData[outputs];

        for(int i=0;i<outputs;i++){
            neuronsData[i] = new NeuronData(0.0, weightsInitializer.initialize(inputs));
        }

        return neuronsData;
    }

    public void setExpectedOutput(double[] expectedOutput) throws InvalidDimensionsException {
        if(outputs != expectedOutput.length){
            throw new InvalidDimensionsException();
        }
        this.expectedOutput = expectedOutput;
    }

    public void setLearningMode(boolean learningMode) {
        this.learningMode = learningMode;
    }

    public void setLearningParameters(LearningParameters learningParameters) {
        this.learningParameters = learningParameters;
    }

    public void calculate(){
        super.calculate();

        if(learningMode){
            int i=0;
            for(Neuron neuron : neurons){
                Map<Neuron, Double> weights = neuron.getWeights();
                for(Neuron prevLayerNeuron : weights.keySet()){
                    double weight = weights.get(prevLayerNeuron);

                    double derivative = 1.0;
                    if(ActivationFunctionType.TANSIG.equals(activationFunctionType)){
                        derivative = 1.0 - Math.pow(new TansigActivationFunciton().calculate(prevLayerNeuron.getOutput()*weight), 2);
                    }
                    double newWeight = weight + learningParameters.getAlpha()*(expectedOutput[i]-prevLayerNeuron.getOutput()*weight)*derivative*prevLayerNeuron.getOutput();
                    try {
                        neuron.updateWeight(prevLayerNeuron, newWeight);
                    } catch (ConnectionNotExistsException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                i++;

            }
        }
    }




}
