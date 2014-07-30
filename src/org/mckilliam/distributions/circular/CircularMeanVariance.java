package org.mckilliam.distributions.circular;

import pubsim.Integration;

/**
 * Compute the circularVariance of the argument of the complex mean estimator
 * given a noise distribution.
 * @author Robert McKilliams
 */
public class CircularMeanVariance {

    protected final CircularRandomVariable f;
    protected final double var;
    protected final double mean;

    public CircularMeanVariance(CircularRandomVariable dist){
        f = dist;

        final int INTEGRAL_STEPS = 10000;
        double Ecos = (new Integration() {
            public double f(double x) {
                return Math.cos(2*Math.PI*x)*f.pdf(x);
            }
        }).trapezoid(-0.5, 0.5, INTEGRAL_STEPS);
        double Esin = (new Integration() {
            public double f(double x) {
                return Math.sin(2*Math.PI*x)*f.pdf(x);
            }
        }).trapezoid(-0.5, 0.5, INTEGRAL_STEPS);

        var = 1 - Math.sqrt( Ecos*Ecos + Esin*Esin);
        mean = Math.atan2(Esin, Ecos)/(2*Math.PI);

    }

    /**
     * Numerically compute the circularVariance of the argument of the complex mean.
     * @return circularVariance of the argument of the complex mean estimator
     */
    public double circularVariance(){
        return var;
    }


    public double circularMean(){
        return mean;
    }


}
