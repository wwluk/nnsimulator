package pl.edu.agh.nnsimulator;

import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InputLayer;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.NetworkLayer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NeuralNetwork {
    private InputLayer inputLayer;
    protected List<NetworkLayer> hiddenLayers = new LinkedList<NetworkLayer>();
    private boolean prepared = false;

    /**
     * Creates neural network
     * @param inputNum number of network inputs
     */
    public NeuralNetwork(int inputNum){
        inputLayer = new InputLayer(inputNum);
    }

    /**
     * Calculates output of the network
     * @return network output
     * @throws InvalidDimensionsException
     */
    public double[] calculate() throws InvalidDimensionsException {
        if(!prepared){
            prepareNetwork();
            prepared = true;
        }

        for(NetworkLayer layer: hiddenLayers){
            layer.calculate();
        }

        return hiddenLayers.get(hiddenLayers.size()-1).getOutput();

    }

    /**
     * Prepares network for calculations - connects neurons etc.
     * Should be called after changing network (adding layers etc.), before calculate()
     * @throws InvalidDimensionsException
     */
    public void prepareNetwork() throws InvalidDimensionsException {
        if(prepared){
            return;
        }
        NetworkLayer previousLayer = inputLayer;
        Iterator layersIterator = hiddenLayers.iterator();
        while(layersIterator.hasNext()){
            NetworkLayer currentLayer = (NetworkLayer)layersIterator.next();
            currentLayer.prepareNetwork(previousLayer);
            previousLayer = currentLayer;
        }
        prepared = true;
    }

    /**
     * Adds hidden or output layer to the network
     * @param layer network layer
     * @throws pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException
     */
    public void addLayer(NetworkLayer layer) throws TooMuchInputLayersException {
        if(layer instanceof InputLayer){
            throw new TooMuchInputLayersException();
        }
        hiddenLayers.add(layer);
    }

    /**
     * Sets inputs to the network
     * @param inputs array of inputs
     * @throws InvalidDimensionsException
     */
    public void setInputs(double[] inputs) throws InvalidDimensionsException {
        inputLayer.setInputs(inputs);
    }


}
