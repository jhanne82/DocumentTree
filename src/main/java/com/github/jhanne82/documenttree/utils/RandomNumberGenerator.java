package com.github.jhanne82.documenttree.utils;

import com.github.jhanne82.documenttree.simulation.Distribution;

import java.util.Random;

public class RandomNumberGenerator {

    private double[] exponentiallyNumberArray;

    private Random random = new Random( System.currentTimeMillis());



    public int getInt(  int maxValue ) {
        return random.nextInt( maxValue );
    }



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



    private void fillExponentiallyNumberArray( int maxValue ) {
        exponentiallyNumberArray = new double[30000];

        int basis = 1;
        for( int i=0; i< exponentiallyNumberArray.length; i++ ) {

            int potent = (int)Math.pow( basis, 3 );

            double doubleValue = getDouble( Distribution.EQUALLY, maxValue );

            while( i> 0 && exponentiallyNumberArray[i-1] > doubleValue ) {
                doubleValue = getDouble( Distribution.EQUALLY, maxValue );
            }

            for( int j = 0; j< potent && i+j < exponentiallyNumberArray.length; j++ ) {

                exponentiallyNumberArray[i+j] = doubleValue;
            }
            i += potent;
            basis++;
        }
    }


}
