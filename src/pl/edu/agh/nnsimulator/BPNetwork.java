package pl.edu.agh.nnsimulator;

import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.activationFunctions.TansigActivationFunciton;
import pl.edu.agh.nnsimulator.exceptions.ConnectionNotExistsException;
import pl.edu.agh.nnsimulator.layers.InputLayer;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.NetworkLayer;
import pl.edu.agh.nnsimulator.neurons.Neuron;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lukasz
 * Date: 18.06.13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class BPNetwork extends NeuralNetwork {

    private double[] expectedOutput;
    private LearningParameters learningParameters;
    private boolean learningMode = false;
    private boolean withBias=true;
    private List<Double> rmsError;

    /**
     * Creates neural network
     *
     * @param inputNum number of network inputs
     */
    public BPNetwork(int inputNum) {
        super(inputNum);
        rmsError = new LinkedList<Double>();
    }

    public void setExpectedOutput(double[] expectedOutput) throws InvalidDimensionsException {
        prepareNetwork();

        int outputs = hiddenLayers.get(hiddenLayers.size()-1).getNeurons().size();
        if (outputs != expectedOutput.length) {
            throw new InvalidDimensionsException();
        }
        this.expectedOutput = expectedOutput;
    }

    public LearningParameters getLearningParameters() {
        return learningParameters;
    }

    public void setLearningParameters(LearningParameters learningParameters) {
        this.learningParameters = learningParameters;
    }

    @Override
    public double[] calculate() throws InvalidDimensionsException {
        double[] result = super.calculate();
        if (learningMode) {
            //set all errors to 0;
            for(NetworkLayer layer : hiddenLayers){
                for(Neuron neuron : layer.getNeurons()){
                    neuron.setError(0);
                }
            }

            List<NetworkLayer> hiddenLayersCopy =
                    (List<NetworkLayer>) ((LinkedList<NetworkLayer>) hiddenLayers).clone();
            Collections.reverse(hiddenLayersCopy);

            //calculate neuron errors
            boolean outputLayer = true;
            for (NetworkLayer layer : hiddenLayersCopy){
                double currentRmsError = 0.0;
                int i=0;
                for(Neuron neuron : layer.getNeurons()){
                    if(outputLayer){
                        neuron.setError(neuron.getOutput()-expectedOutput[i]);
                        currentRmsError += Math.pow(neuron.getOutput()-expectedOutput[i], 2);
                    }

                    double error = neuron.getError();
                    for(Neuron prevLayerNeuron : neuron.getWeights().keySet()){
                        double prevWeight = neuron.getWeights().get(prevLayerNeuron);
                        prevLayerNeuron.updateError(error*prevWeight);
                    }
                    i++;
                }
                if(outputLayer){
                    rmsError.add(Math.sqrt(currentRmsError/i));
                    outputLayer = false;
                }

            }

            for(NetworkLayer layer : hiddenLayers){
                for(Neuron neuron : layer.getNeurons()){
                    double error = neuron.getError();
                    double derivative = 1.0;
                    if(ActivationFunctionType.TANSIG.equals(layer.getActivationFunctionType())){
                        derivative = 1.0 - Math.pow(new TansigActivationFunciton().calculate(neuron.calculateInput()), 2);
                    }
                    for(Neuron prevLayerNeuron : neuron.getWeights().keySet()){
                        double prevWeight = neuron.getWeights().get(prevLayerNeuron);
                        try {
                            neuron.updateWeight(prevLayerNeuron, prevWeight - error*derivative*learningParameters.getAlpha()*prevLayerNeuron.getOutput() + learningParameters.getMomentum()*neuron.getPreviousWeightChange(prevLayerNeuron));
                        } catch (ConnectionNotExistsException e) {
                            e.printStackTrace();
                        }
                    }

                    if(withBias){
                        neuron.setBias(neuron.getBias() - error*derivative*learningParameters.getAlpha() + learningParameters.getMomentum()*neuron.getPreviousBiasChange());
                    }

                    neuron.calculate();
                }
            }

        }
        return result;
    }

    public void setLearningMode(boolean learningMode) {
        this.learningMode = learningMode;
    }

    public boolean isWithBias() {
        return withBias;
    }

    public void setWithBias(boolean withBias) {
        this.withBias = withBias;
    }

    public String printRmsError(int period){
        StringBuilder sb = new StringBuilder();
        int i=0;
        for(double err : rmsError){
            if(i%period!=0 && i != rmsError.size()-1){
                i++;
                continue;
            }

            sb.append("Epoka ").append(i++).append(": ").append(err).append(System.getProperty("line.separator"));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String printRmsError(){
        return printRmsError(1000);
    }
}
