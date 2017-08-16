package com.github.jhanne82.documenttree.utils;

import com.github.jhanne82.documenttree.simulation.Distribution;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {


    private Distribution distribution;
    private int maxValue;


    private Random random;



    public RandomNumberGenerator( Distribution distribution, int maxValue ) {
        this.distribution = distribution;
        this.maxValue = maxValue;

        random = new Random( System.currentTimeMillis() );
    }


    public int getInt() {
        return random.nextInt( maxValue );
    }

    public double getDouble() {
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
