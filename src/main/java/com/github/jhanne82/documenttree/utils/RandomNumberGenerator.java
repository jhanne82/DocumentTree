package com.github.jhanne82.documenttree.utils;

import com.github.jhanne82.documenttree.enumeration.Distribution;

import java.util.Random;



/**
 * Implementation to generate a stream of pseudorandom numbers. For pseudorandom {@code double} value the generations
 * relates to different distributions.
 * This classes uses the current timestamp as milliseconds as seed for the pseudorandom number generation.
 *
 */
public class RandomNumberGenerator {



    private Random   random                   = new Random( System.currentTimeMillis());
    private double[] exponentiallyNumberArray;



    /**
     * Returns a pseudorandom {@code int} value between 0 (inclusive) and the specified maxValue (exclusive).
     * @param maxValue is the exclusive upper bound, must be >0
     * @return the next pseudorandom {@code int} value from this random number generator's sequence
     */
    public int getInt(  int maxValue ) {
        return random.nextInt( maxValue );
    }



    /**
     * Returns a pseudorandom {@code double} value between 0 (inclusive) and the specified maxValue (exclusive) related
     * to the defined distribution.
     * @param distribution defines the distribution of the generated pseudorandim {@code double} double value:
     * {@code EQUALLY}, {@code GAUSSIAN}, {@code EXPONENTIALLY}
     * @param maxValue is the exclusive upper bound, must be >0
     * @return the next pseudorandom {@code double} value from this random number generator's sequence
     */
    public double getDouble( Distribution distribution, int maxValue ) {
        double minValue = 0.1;
        switch ( distribution ) {
            case EQUALLY:
                return minValue + ( maxValue - minValue) * random.nextDouble();
            case GAUSSIAN:
                return minValue + ( maxValue - minValue) * random.nextGaussian();
            case EXPONENTIALLY:
                if( exponentiallyNumberArray == null ) {
                    fillExponentiallyNumberArray( maxValue );
                }
                return exponentiallyNumberArray[getInt(exponentiallyNumberArray.length)];
            default:
                throw new UnsupportedOperationException();
        }
    }



    // helper method to generate an array which contains pseudogeneric double values where the
    // quantity of each value increases exponentially
    private void fillExponentiallyNumberArray( int maxValue ) {
        exponentiallyNumberArray = new double[30000];

        int basis = 1;
        for( int i=0; i< exponentiallyNumberArray.length; i++ ) {

            int potent = (int)Math.pow( basis, 3 );

            double doubleValue = getDouble( Distribution.EQUALLY, maxValue );

            // ensure the generated double value is greater then the last one, otherwise generate a new double value
            while( i> 0 && exponentiallyNumberArray[i-1] > doubleValue ) {
                doubleValue = getDouble( Distribution.EQUALLY, maxValue );
            }

            // store pseudorandom double value in the quantitiy of the potent
            for( int j = 0; j< potent && i+j < exponentiallyNumberArray.length; j++ ) {
                exponentiallyNumberArray[i+j] = doubleValue;
            }
            i += potent;
            basis++;
        }
    }
}