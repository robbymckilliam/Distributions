/*
 * AbstractRealRandomVariable.java
 *
 * Created on 23 October 2007, 15:28
 */

package org.mckilliam.distributions;

import pubsim.Complex;
import org.mckilliam.distributions.circular.CircularRandomVariable;
import org.mckilliam.distributions.circular.WrappedCircularRandomVariable;
import org.mckilliam.optimisation.Bisection;
import org.mckilliam.optimisation.SingleVariateFunction;
import pubsim.Integration;
import rngpack.RandomElement;
import rngpack.Ranmar;

/**
 * Class that contains some standard functions for noise generators
 * @author Robby McKilliam
 */
public abstract class AbstractRealRandomVariable
        implements RealRandomVariable {
    
    protected RandomElement random;
    
    public AbstractRealRandomVariable(){
        random = new Ranmar(SeedGenerator.getSeed());
    }
    
    /**
     * Take standard inverse cumulative density function approach
     * by default.
     */
    @Override
    public Double noise(){
        return icdf(random.raw());
    }

    /**
     * Integrate the pdf by default.  This is highly non-optimised.
     */
    @Override
    public double cdf(Double x){
        double startint = mean() - 20*Math.sqrt(variance());
        final int INTEGRAL_STEPS = 1000;
        Integration integral = new Integration() {
            public double f(double x) {
                return pdf(x);
            }
        };  
        return integral.trapezoid(startint, x,INTEGRAL_STEPS);
    }

    /**
     * Bisection method is used to inverse cdf by default.
     * This might fail for really weird looking cdfs.
     */
    @Override
    public Double icdf(final double v){
                
        //function representing the cdf (Java is horrible at this!)
        SingleVariateFunction f = new SingleVariateFunction() {
            @Override
            public double value(double x) {
                return cdf(x) - v;
            }
        };
        
        //find starting point for bisection.  Move out from the origin in exponentially increasing steps.
        double a = -1.0, b = 1.0;
        while( Math.signum(f.value(a)) == Math.signum(f.value(b))) {
            a *= 10;
            b *= 10;
        }
        
        return Bisection.zero(f, a, b, 1e-8); //1e-8 tolerance by default (hopefully accurate enough for most purposes
    }
    
    /** Randomise the seed for the internal Random */ 
    @Override
    public void randomSeed(){ random = new Ranmar(new java.util.Date()); }

    
    /** Set the seed for the internal Random */
    @Override
    public void setSeed(long seed) { random = new Ranmar(seed); }
    
    /** Default is the return the wrapped version of this random variable */
    @Override
    public CircularRandomVariable wrapped() {
        return new WrappedCircularRandomVariable(this);
    }
    
    /** 
     * Numerical integration to compute characteristic function.
     * This is very approximate, as it guesses an interval to integrate over.
     */
    @Override
    public Complex characteristicFunction(Double t){
        final double ft = t;
        int integralsteps = 5000;
        double startint = mean() - 30*Math.sqrt(variance());
        double endint = mean() + 30*Math.sqrt(variance());
        double rvar = (new Integration() {
            public double f(double x) {
                return Math.cos(ft*x)*pdf(x);
            }
        }).trapezoid(startint, endint,integralsteps);
        double cvar = (new Integration() {
            public double f(double x) {
                return Math.sin(ft*x)*pdf(x);
            }
        }).trapezoid(startint, endint,integralsteps);
        return new Complex(rvar, cvar);
    }

    
}
