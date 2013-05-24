package pl.edu.agh.nnsimulator.layers;

import pl.edu.agh.nnsimulator.neurons.Input;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;

import java.util.Iterator;

public class InputLayer extends NetworkLayer{
    private boolean normalize = true;

    public InputLayer(int inputsNum){
        super(ActivationFunctionType.PURELIN, null);

        for(int i=0;i<inputsNum;i++){
            neurons.add(new Input());
        }
    }

    public void setInputs(double[] inputs) throws InvalidDimensionsException {
        if(inputs.length != neurons.size()){
            throw new InvalidDimensionsException();
        }
        Iterator inputNeuronsIterator = neurons.iterator();

        if(normalize){

            double norm = 0.0;
            for(double input :inputs){
                norm += input*input;
            }
            norm = Math.sqrt(norm);

            for(double input: inputs){
                ((Input)inputNeuronsIterator.next()).setInput(input/norm);
            }
        }else{

            for(double input: inputs){
                ((Input)inputNeuronsIterator.next()).setInput(input);
            }
        }
    }
}
