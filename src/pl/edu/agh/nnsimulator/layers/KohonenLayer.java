package pl.edu.agh.nnsimulator.layers;

import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.neurons.Neuron;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializators.WeightsInitializer;

import java.util.Iterator;
import java.util.Random;

public class KohonenLayer extends NetworkLayer{
    private boolean learningMode = true;
    private Neuron[][] neuronsArray = null;
    private int rows, cols;


    public KohonenLayer(int inputs, int rows, int cols, WeightsInitializer weightsInitializer){
        super(ActivationFunctionType.PURELIN, initializeWeights(inputs, rows, cols, weightsInitializer));
        this.rows = rows;
        this.cols = cols;
    }


    public boolean isLearningMode() {
        return learningMode;
    }

    public void setLearningMode(boolean learningMode) {
        this.learningMode = learningMode;
    }

    @Override
    public double[] getOutput() {
        double[] output = super.getOutput();
        double maxValue = output[0];
        int maxPosition = 0;
        for(int i=1;i<output.length;i++){
            if(output[i] > maxValue){
                maxValue = output[i];
                maxPosition = i;
            }
        }

        double[] normalizedOutput = new double[output.length];
        for(int i=0;i<output.length;i++){
            normalizedOutput[i] = 0.0;
        }
        normalizedOutput[maxPosition] = 1.0;

        return normalizedOutput;
    }

    @Override
    public void calculate() {
        super.calculate();    //To change body of overridden methods use File | Settings | File Templates.
        if(learningMode){
            if(neuronsArray == null){
                prepareLayer();
            }
            //TODO perform weights changes
        }
    }


    private void prepareLayer() {
        neuronsArray = new Neuron[rows][cols];
        Iterator<Neuron> neuronIt = neurons.iterator();
        for(int row=0; row<rows; row++){
            for(int col=0; col<cols; col++){
                neuronsArray[row][col] = neuronIt.next();
            }
        }
    }

    private static NeuronData[] initializeWeights(int inputs, int rows, int cols, WeightsInitializer weightsInitializer) {
        NeuronData[] neuronsData = new NeuronData[rows*cols];

        for(int i=0;i<rows*cols;i++){
            neuronsData[i] = new NeuronData(0.0, weightsInitializer.initialize(inputs));
        }

        return neuronsData;
    }
}
