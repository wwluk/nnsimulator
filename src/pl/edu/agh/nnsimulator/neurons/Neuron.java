package pl.edu.agh.nnsimulator.neurons;

import pl.edu.agh.nnsimulator.activationFunctions.*;

import java.util.HashMap;
import java.util.Map;

public class Neuron {
    private Map<Neuron, Double> connections = new HashMap<Neuron, Double>();

    private ActivationFunctionType activationFunctionType;
    private ActivationFunctionInterface activationFunction;
    private double bias;
    protected double output;

    public Neuron(ActivationFunctionType activationFunctionType, double bias, Map<Neuron, Double> connections){
        this.activationFunctionType = activationFunctionType;
        this.bias = bias;
        this.connections = connections;

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

    public double calculate(){
        double result = 0;
        for(Neuron neuron : connections.keySet()){
            result +=neuron.getOutput()*connections.get(neuron);

        }
        result += bias;
        output = activationFunction.calculate(result);

        return output;
    }

    public double getOutput(){
        return output;
    }


}
