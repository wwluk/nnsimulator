package pl.edu.agh.nnsimulator;

public class LearningParameters {
    private double alpha;
    private int neighborhood;

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(int neighborhood) {
        this.neighborhood = neighborhood;
    }
}
