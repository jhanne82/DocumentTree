package com.github.jhanne82.documenttree.utils;

import com.github.jhanne82.documenttree.simulation.Distribution;

import java.util.Random;

public class RandomNumberGenerator {


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
            default:
                throw new UnsupportedOperationException();

        }
    }


}
