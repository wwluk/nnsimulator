package pl.edu.agh.nnsimulator.neurons;

public class NeuronData {
    private double bias;
    private double[] weights;

    public NeuronData(double bias, double[] weights) {
        this.bias = bias;
        this.weights = weights;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }
}
