package com.github.jhanne82.documenttree.numbergenerator;

import java.security.SecureRandom;
import java.util.Random;

public class RandomNumberGenerator {


    private Distribution distribution;
    private int maxValue;


    private Random random;


    // TODO beachte Verteilung
    public RandomNumberGenerator( Distribution distribution, int maxValue ) {
        this.distribution = distribution;
        this.maxValue = maxValue;

        random = new Random( System.currentTimeMillis() );
    }


    public int getInt() {
        return random.nextInt( maxValue );
    }


}
