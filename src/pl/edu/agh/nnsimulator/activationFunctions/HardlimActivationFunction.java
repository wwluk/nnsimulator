package pl.edu.agh.nnsimulator.activationFunctions;

public class HardlimActivationFunction implements ActivationFunctionInterface{

    @Override
    public double calculate(double input) {
        if(input>0){
            return 1;
        }else{
            return 0;
        }
    }
}
