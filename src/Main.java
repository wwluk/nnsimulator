import pl.edu.agh.nnsimulator.*;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.NetworkLayer;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializers.RandomWeightsInitializer;
import pl.edu.agh.nnsimulator.weightsInitializers.ZeroWeightsInitializer;

public class Main {
    public static void main(String[] args) throws TooMuchInputLayersException, InvalidDimensionsException {
        NeuralNetwork nn = new NeuralNetwork(2);

        nn.addLayer(new NetworkLayer(ActivationFunctionType.PURELIN,
                new NeuronData[]{new NeuronData(1.0, new double[]{2.0, 1.0})}));

        nn.addLayer(new NetworkLayer(ActivationFunctionType.HARDLIM,
                new NeuronData[]{new NeuronData(-4.5, new double[]{1.0})}));

        nn.addLayer(new NetworkLayer(ActivationFunctionType.TANSIG,
                new NeuronData[]{
                        new NeuronData(-2.3, new double[]{1.3}),
                        new NeuronData(-0.4, new double[]{1.4})
                }));

        double[] inputs = {1, 0};
        nn.setInputs(inputs);
        double[] outputs = nn.calculate();
        for(Double output : outputs){
            System.out.println(output);
        }



        System.out.println("AND: ");
        NeuralNetwork and = new NeuralNetwork(2);
        and.addLayer(new NetworkLayer(ActivationFunctionType.PURELIN,
                new NeuronData[]{new NeuronData(-0.0196, new double[]{0.0614, 0.3297})}));

        and.setInputs(new double[]{0.0, 0.0});
        System.out.println(and.calculate()[0]);

        and.setInputs(new double[]{0.0, 1.0});
        System.out.println(and.calculate()[0]);

        and.setInputs(new double[]{1.0, 0.0});
        System.out.println(and.calculate()[0]);

        and.setInputs(new double[]{1.0, 1.0});
        System.out.println(and.calculate()[0]);

        //Kohonen
        System.out.println("Kohonen:");
        KohonenNetwork kohonenNetwork = new KohonenNetwork(4,1,4,new RandomWeightsInitializer(-0.1,1.1));
//        KohonenNetwork kohonenNetwork = new KohonenNetwork(4,1,3,new ZeroWeightsInitializer());

        kohonenTest(kohonenNetwork, new double[]{1,1,0,0});
        kohonenTest(kohonenNetwork, new double[]{1,1,1,0});
        kohonenTest(kohonenNetwork, new double[]{0,1,1,1});
        kohonenTest(kohonenNetwork, new double[]{0,0,1,1});

        LearningParameters learningParameters = new LearningParameters();
        learningParameters.setAlpha(0.3);
        learningParameters.setNeighborhood(2);
        kohonenNetwork.setLearningParametrs(learningParameters);
        kohonenNetwork.setLearningMode(true);
        for(int i=0;i<30000;i++){
            kohonenNetwork.setInputs(new double[]{1,1,0,0});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{1,1,1,0});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{0,1,1,1});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{0,0,1,1});
            kohonenNetwork.calculate();
        }

        learningParameters.setNeighborhood(1);
        learningParameters.setAlpha(0.15);
        for(int i=0;i<100000;i++){
            kohonenNetwork.setInputs(new double[]{1,1,0,0});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{1,1,1,0});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{0,1,1,1});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{0,0,1,1});
            kohonenNetwork.calculate();
        }

        learningParameters.setNeighborhood(0);
        learningParameters.setAlpha(0.1);
        for(int i=0;i<10000;i++){
            kohonenNetwork.setInputs(new double[]{1,1,0,0});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{1,1,1,0});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{0,1,1,1});
            kohonenNetwork.calculate();
            kohonenNetwork.setInputs(new double[]{0,0,1,1});
            kohonenNetwork.calculate();
        }

        System.out.println("After learning:");
        kohonenNetwork.setLearningMode(false);
        kohonenTest(kohonenNetwork, new double[]{1,1,0,0});
        kohonenTest(kohonenNetwork, new double[]{1,1,1,0});
        kohonenTest(kohonenNetwork, new double[]{0,1,1,1});
        kohonenTest(kohonenNetwork, new double[]{0,0,1,1});


    }

    private static void kohonenTest(KohonenNetwork kohonenNetwork, double[] inputs) throws InvalidDimensionsException {
        StringBuilder sb = new StringBuilder();
        for(double input: inputs){
            sb.append(input).append("  ");
        }
        System.out.println("Testing Kohonen with input " + sb.toString());

        kohonenNetwork.setInputs(inputs);
        double[] outputs = kohonenNetwork.calculate();
        for(double output : outputs){
            System.out.println(output);
        }
    }
}

