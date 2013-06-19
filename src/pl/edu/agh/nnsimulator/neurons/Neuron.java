package pl.edu.agh.nnsimulator.neurons;

import pl.edu.agh.nnsimulator.activationFunctions.*;
import pl.edu.agh.nnsimulator.exceptions.ConnectionNotExistsException;

import java.util.HashMap;
import java.util.Map;

public class Neuron {
    private Map<Neuron, Double> weights;
    private ActivationFunctionType activationFunctionType;
    private ActivationFunctionInterface activationFunction;
    private double bias;
    protected double output;
    private double error;
    private Map<Neuron, Double> previousChange;
    private double previousBiasChange = 0.0;

    public Neuron(ActivationFunctionType activationFunctionType, double bias, Map<Neuron, Double> weights){
        this.activationFunctionType = activationFunctionType;
        this.bias = bias;
        this.weights = new HashMap<Neuron, Double>();
        this.weights = weights;
        this.previousChange = new HashMap<Neuron, Double>();

        switch (activationFunctionType){
            case PURELIN:
                activationFunction = new PurelinActivationFunction();
                break;
            case HARDLIM:
                activationFunction = new HardlimActivationFunction();
                break;
            case TANSIG:
                activationFunction = new TansigActivationFunciton();
                break;
        }
    }

    public double calculateInput(){
        double result = 0.0;
        for(Neuron neuron : weights.keySet()){
            result +=neuron.getOutput()* weights.get(neuron);

        }
        result += bias;
        return result;
    }

    public double calculate(){
        output = activationFunction.calculate(calculateInput());
        return output;
    }

    public double getOutput(){
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public Map<Neuron, Double> getWeights() {
        return new HashMap<Neuron, Double>(weights);
    }

    public void updateWeight(Neuron neuron, double weight) throws ConnectionNotExistsException {
        if(!weights.containsKey(neuron)){
            throw new ConnectionNotExistsException();
        }
        previousChange.put(neuron, weight - weights.get(neuron));
        weights.put(neuron,weight);

             /*
        System.out.println("---WEIGHTS---");
        for(double w : weights.values()){
            System.out.println(w);

        }      */
    }

    public void normalize(){
        double norm = 0.0;
        for(double weight : weights.values()){
            norm += weight*weight;
        }
        norm = Math.sqrt(norm);
        if(norm > 0){
            for(Neuron neuron : weights.keySet()){
                weights.put(neuron, weights.get(neuron)/norm);
            }
        }
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public void updateError(double error){
        this.error += error;
    }

    public double getPreviousWeightChange(Neuron neuron){
        if(previousChange.containsKey(neuron)){
            return previousChange.get(neuron);
        }
        return 0;
    }

    public void setBias(double bias){
        previousBiasChange = bias - this.bias;
        this.bias = bias;
    }

    public double getBias(){
        return bias;
    }
    public double getPreviousBiasChange(){
        return previousBiasChange;
    }


}
