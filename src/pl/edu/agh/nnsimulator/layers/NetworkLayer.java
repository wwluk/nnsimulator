package pl.edu.agh.nnsimulator.layers;

import pl.edu.agh.nnsimulator.neurons.Neuron;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;

import java.util.*;

public class NetworkLayer{

    private NeuronData[] neuronsData;
    private ActivationFunctionType activationFunctionType;
    protected List<Neuron> neurons = new LinkedList<Neuron>();

    /**
     * Creates layer of the neural network
     * @param activationFunctionType
     * @param neuronsData
     */
    public NetworkLayer(ActivationFunctionType activationFunctionType, NeuronData[] neuronsData) {
        this.activationFunctionType = activationFunctionType;
        this.neuronsData = neuronsData;
    }

    /**
     * Prepares layer before calculations (connects neurons etc.)
     * @param previousLayer
     * @throws InvalidDimensionsException
     */
    public void prepareNetwork(NetworkLayer previousLayer) throws InvalidDimensionsException {
        List<Neuron> prevLayerNeurons = previousLayer.getNeurons();

        //create and connect neurons
        for(NeuronData neuronData : neuronsData){
            if(prevLayerNeurons.size() != neuronData.getWeights().length){
                throw new InvalidDimensionsException();
            }

            Map<Neuron, Double> neuronWeights = new HashMap<Neuron, Double>();
            for(int i=0;i<prevLayerNeurons.size(); i++){
                neuronWeights.put(prevLayerNeurons.get(i), neuronData.getWeights()[i]);
            }
            neurons.add(new Neuron(activationFunctionType, neuronData.getBias(), neuronWeights));
        }
    }

    /**
     * perform calculations
     */
    public void calculate(){
        for(Neuron neuron : neurons){
            neuron.calculate();
        }
    }

    /**
     * Return neurons in layer (prepareNetwork() must be called first!)
     * @return list of neurons in layer
     */
    public List<Neuron> getNeurons(){
        return neurons;
    }

    /**
     * Returns output from neurons in this layer
     * @return double[] output
     */
    public double[] getOutput() {
        double[] outputs = new double[neurons.size()];

        int i=0;
        for(Neuron neuron : neurons){
            outputs[i] = neuron.getOutput();
            i++;
        }

        return outputs;
    }
}
