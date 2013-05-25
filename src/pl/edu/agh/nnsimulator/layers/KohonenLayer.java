package pl.edu.agh.nnsimulator.layers;

import au.com.bytecode.opencsv.bean.MappingStrategy;
import pl.edu.agh.nnsimulator.LearningParameters;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.ConnectionNotExistsException;
import pl.edu.agh.nnsimulator.neurons.Neuron;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class KohonenLayer extends NetworkLayer{
    private boolean normalize = true;
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
        return super.getOutput();
    /*
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

        return normalizedOutput;   */
         /*
        int bestPosition = 0;
        double minDist = calculateDist(neurons.get(bestPosition).getWeights());

        int i=0;
        for(Neuron neuron : neurons){
            double dist = calculateDist(neuron.getWeights());
            if(dist < minDist){
                bestPosition = i;
                minDist = dist;
            }
            i++;
        }

        double[] normalizedOutput = new double[neurons.size()];
        for(i=0;i<neurons.size();i++){
            normalizedOutput[i] = 0.0;
        }
        normalizedOutput[bestPosition] = 1.0;

        return normalizedOutput;
        */
    }

    @Override
    public void calculate() {
        super.calculate();

        if(learningMode){
            if(neuronsArray == null){
                prepareLayer();
            }

            if(normalize){
                for(Neuron neuron : neurons){
                    neuron.normalize();
                }
            }

            int bestRow=0, bestCol=0;
            double minDist = Double.MAX_VALUE;

            for(int row=0; row<rows; row++){
                for(int col=0; col<cols; col++){
                    double dist = calculateDist(neuronsArray[row][col].getWeights());
                    if(dist < minDist){
                        minDist = dist;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }

            Neuron bestNeuron = neuronsArray[bestRow][bestCol];
            Map<Neuron, Double> weights = bestNeuron.getWeights();
            for (Neuron prevLayerNeuron : weights.keySet()) {
                double prevWeight = weights.get(prevLayerNeuron);
                try {
                    double newWeight = prevWeight + learningParameters.getAlpha() * (prevLayerNeuron.getOutput() - prevWeight);
                    bestNeuron.updateWeight(prevLayerNeuron, newWeight);
                } catch (ConnectionNotExistsException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (Math.abs(row - bestRow) <= learningParameters.getNeighborhood() && Math.abs(col - bestCol) <= learningParameters.getNeighborhood()) {
                        updateWeights(neuronsArray[row][col], Math.max(Math.abs(row - bestRow), Math.abs(col - bestCol)));
                    }
                }
            }


        }

        int bestPosition = 0;
        double minDist = Double.MAX_VALUE;

        int i=0;
        for(Neuron neuron : neurons){
            double dist = calculateDist(neuron.getWeights());
            if(dist < minDist){
                bestPosition = i;
                minDist = dist;
            }
            i++;
        }

        for(Neuron neuron : neurons){
            neuron.setOutput(0.0);
        }
        neurons.get(bestPosition).setOutput(1.0);
//        System.out.println(bestPosition);

           /*
        double maxValue = Double.MIN_VALUE;
        int maxPosition = 0;
        int i=0;
        for(Neuron neuron : neurons){
            if(neuron.getOutput() > maxValue){
                maxValue = neuron.getOutput();
                maxPosition = i;
            }
            i++;
        }


        for(Neuron neuron : neurons){
            neuron.setOutput(0.0);
        }
        neurons.get(maxPosition).setOutput(1.0);
        */
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

    private void updateWeights(Neuron neuron, int neighborhood){
        Map<Neuron, Double> weights = neuron.getWeights();
        for(Neuron prevLayerNeuron : weights.keySet()){
            double prevWeight = weights.get(prevLayerNeuron);
            try {
                double newWeight = prevWeight + (1.0/(1.0+neighborhood)) * learningParameters.getAlpha() * (prevLayerNeuron.getOutput() - prevWeight);
                neuron.updateWeight(prevLayerNeuron, newWeight);
            } catch (ConnectionNotExistsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public LearningParameters getLearningParameters() {
        return learningParameters;
    }

    public void setLearningParameters(LearningParameters learningParameters) {
        this.learningParameters = learningParameters;
    }
}
