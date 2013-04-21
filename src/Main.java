import pl.edu.agh.nnsimulator.*;
import pl.edu.agh.nnsimulator.activationFunctions.ActivationFunctionType;
import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.InvalidDimensionsException;
import pl.edu.agh.nnsimulator.layers.KohonenLayer;
import pl.edu.agh.nnsimulator.layers.NetworkLayer;
import pl.edu.agh.nnsimulator.neurons.NeuronData;
import pl.edu.agh.nnsimulator.weightsInitializators.RandomWeightsInitializer;

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
        NeuralNetwork kohonenNetwork = new KohonenNetwork(4,1,3,new RandomWeightsInitializer(-0.5,0.5));
        kohonenNetwork.setInputs(new double[]{1,0,0,0});
        outputs = kohonenNetwork.calculate();
        System.out.println(outputs[0]);
        System.out.println(outputs[1]);
        System.out.println(outputs[2]);
    }


}

