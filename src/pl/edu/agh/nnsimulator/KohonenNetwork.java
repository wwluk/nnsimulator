package pl.edu.agh.nnsimulator;

import pl.edu.agh.nnsimulator.exceptions.TooMuchInputLayersException;
import pl.edu.agh.nnsimulator.layers.KohonenLayer;
import pl.edu.agh.nnsimulator.weightsInitializers.WeightsInitializer;

public class KohonenNetwork extends NeuralNetwork{
    private KohonenLayer kohonenLayer;
    public KohonenNetwork(int inputNum, int rows, int cols, WeightsInitializer weightsInitializer){
        super(inputNum);
        kohonenLayer = new KohonenLayer(inputNum, rows, cols, weightsInitializer);
        try {
            this.addLayer(kohonenLayer);
        } catch (TooMuchInputLayersException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
