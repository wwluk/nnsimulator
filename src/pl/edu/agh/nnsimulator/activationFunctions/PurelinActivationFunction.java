package pl.edu.agh.nnsimulator.activationFunctions;

public class PurelinActivationFunction implements ActivationFunctionInterface {
    @Override
    public double calculate(double input) {
        return input;
    }
}
