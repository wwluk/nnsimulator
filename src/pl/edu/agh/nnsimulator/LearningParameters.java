package pl.edu.agh.nnsimulator;

public class LearningParameters {
    private double alpha;
    private int neighborhood;
    private double momentum;

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

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }
}
