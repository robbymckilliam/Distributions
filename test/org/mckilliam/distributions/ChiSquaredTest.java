package org.mckilliam.distributions;

import pubsim.Integration;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class ChiSquaredTest {
    
    public ChiSquaredTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getVariance method, of class ChiSquared.
     */
    @Test
    public void testGetVariance() {
        System.out.println("getVariance");
        ChiSquared instance = new ChiSquared(4, 0.5);
        int iters = 50000;
        double sum = 0.0;
        for(int i = 0; i < iters; i++){
            double val = instance.noise() - instance.mean();
            sum += val*val;
        }
        double sampvar = sum/iters;
        assertEquals(instance.variance(), sampvar,0.05);

    }

    /**
     * Test of pdf method, of class ChiSquared.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");
        Double x = null;
        final ChiSquared instance = new ChiSquared(5,0.4);
        final int INTEGRAL_STEPS = 20000;
        
        //check mean numerically
        double meanint = (new Integration() {
                public double f(double x) {
                    return x*instance.pdf(x);
                }
            }).trapezoid(0, 500, INTEGRAL_STEPS);
        assertEquals(instance.mean(), meanint,0.005);
        
        //check variance numerically
        double varint = (new Integration() {
                public double f(double x) {
                    double xm = x - instance.mean();
                    return xm*xm*instance.pdf(x);
                }
            }).trapezoid(0, 500, INTEGRAL_STEPS);
        assertEquals(instance.variance(), varint,0.005);
        
        
        final ChiSquared instance2 = new ChiSquared.ChiSquared2(0.333);
        
        //check mean numerically
        double meanint2 = (new Integration() {
                public double f(double x) {
                    return x*instance2.pdf(x);
                }
            }).trapezoid(0, 500, INTEGRAL_STEPS);
        assertEquals(instance2.mean(), meanint2,0.005);
        
        //check variance numerically
        double varint2 = (new Integration() {
                public double f(double x) {
                    double xm = x - instance2.mean();
                    return xm*xm*instance2.pdf(x);
                }
            }).trapezoid(0, 500, INTEGRAL_STEPS);
        assertEquals(instance2.variance(), varint2,0.005);
        
    }

}
