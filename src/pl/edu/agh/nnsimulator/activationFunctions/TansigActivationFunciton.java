package pl.edu.agh.nnsimulator.activationFunctions;

public class TansigActivationFunciton implements ActivationFunctionInterface {
    @Override
    public double calculate(double input) {
        return 2/(1+Math.exp(-2*input))-1;
    }
}
