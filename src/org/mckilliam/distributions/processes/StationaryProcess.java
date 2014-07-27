/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mckilliam.distributions.processes;

import org.mckilliam.distributions.NoiseGenerator;
import org.mckilliam.distributions.RealRandomVariable;

/**
 * Interface for a stationary process
 * @author Robby McKilliam
 */
public interface StationaryProcess extends NoiseGenerator<Double> {
    
    /** Return the random variable with the marginal pdf/cdf of this process */
    public RealRandomVariable marginal();
    
    public double[] autocorrelation();
    
}
