package pl.edu.agh.nnsimulator.layers;

import pl.edu.agh.nnsimulator.LearningParameters;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.ConnectionNotExistsException;
import pl.edu.agh.nnsimulator.neurons.Neuron;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;

import java.util.Iterator;
import java.util.Map;

public class KohonenLayer extends NetworkLayer{
    private boolean learningMode = false;
    private Neuron[][] neuronsArray = null;
    private int rows, cols;
    private LearningParameters learningParameters;


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
        super.calculate();

        if(learningMode){
            if(neuronsArray == null){
                prepareLayer();
            }

            int bestRow=0, bestCol=0;
            double minDist = Double.MAX_VALUE;

            for(int row=0; row<rows; row++){
                for(int col=0; col<cols; col++){
                    double dist = calculateDist(neuronsArray[row][col].getWeights());
                    if(dist > minDist){
                        minDist = dist;
                        bestRow = row;
                        bestCol = col;
                    }
                }

                Neuron bestNeuron = neuronsArray[bestRow][bestCol];
                Map<Neuron, Double> weights = bestNeuron.getWeights();
                for(Neuron prevLayerNeuron : weights.keySet()){
                    double prevWeight = weights.get(prevLayerNeuron);
                    try {
                        double newWeight = prevWeight + learningParameters.getAlpha() * (prevLayerNeuron.getOutput() - prevWeight);
                        bestNeuron.updateWeight(prevLayerNeuron, newWeight);
                    } catch (ConnectionNotExistsException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

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

    private double calculateDist(Map<Neuron, Double> weights){
        double sum=0;
        for(Neuron neuron : weights.keySet()){
            sum += Math.pow(neuron.getOutput()-weights.get(neuron),2);
        }
        return Math.sqrt(sum);
    }

    public LearningParameters getLearningParameters() {
        return learningParameters;
    }

    public void setLearningParameters(LearningParameters learningParameters) {
        this.learningParameters = learningParameters;
    }
}
