package pl.edu.agh.nnsimulator.weightsInitializators;

public class ZeroWeightsInitializer implements WeightsInitializer {

    @Override
    public double[] initialize(int inputs) {
        double[] weights =  new double[inputs];
        for(int i=0;i<inputs;i++){
            weights[i] = 0.0;
        }
        return weights;
    }
}
