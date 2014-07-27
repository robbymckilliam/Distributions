/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mckilliam.distributions.complex;

import pubsim.Complex;
import org.mckilliam.distributions.GenericRandomVariable;
import org.mckilliam.distributions.RealRandomVariable;
import org.mckilliam.distributions.circular.CircularRandomVariable;

/**
 *
 * @author Robby McKilliam
 */
public interface ComplexRandomVariable extends GenericRandomVariable<Complex> {
    
    public RealRandomVariable realMarginal();
    public RealRandomVariable imaginaryMarginal();
    public RealRandomVariable magnitudeMarginal();
    public CircularRandomVariable angleMarginal();
    
}
