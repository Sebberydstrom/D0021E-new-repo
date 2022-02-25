package Labb2;

import java.util.Random;

public class GaussianGenerator extends TimerGenerator {
    private Random _generator = new Random();
    // Mean of the Gaussian distribution.
    private double _mean = 0.0;
    // Standard deviation of the Gaussian distribution.
    private double _stddev = 0.0;

    public GaussianGenerator(int network, int node, int number, int startSeq) {
        super( network, node, number, 0, startSeq );
    }

    public void setGauss(double mean, double dev) {
        _mean = mean;
        _stddev = dev;
    }

    //Create random Gaussian generator.
    public double getGaussianTime() {
        return _generator.nextGaussian(_mean, _stddev);
    }
}
