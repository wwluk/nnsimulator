package pl.edu.agh.nnsimulator.neurons;

import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;

public class Input extends Neuron {

    public Input(){
        super(ActivationFunctionType.PURELIN, 0, null);
    }

    public void setInput(double input){
        this.output = input;
    }

    public double calculate(){
        return output;
    }

}
