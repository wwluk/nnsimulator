package pl.edu.agh.nnsimulator.weightsInitializers;

import java.util.Random;

public class RandomWeightsInitializer implements WeightsInitializer {
    private double min, max;
    private Random random;

    public RandomWeightsInitializer(double min, double max){
        this.min = min;
        this.max = max;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public double[] initialize(int inputs) {
        double[] weights = new double[inputs];
        for(int i=0;i<inputs;i++){
            weights[i] = min + (max-min)*random.nextDouble();
        }

        return weights;
    }
}
