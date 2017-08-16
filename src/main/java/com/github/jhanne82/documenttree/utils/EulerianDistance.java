package com.github.jhanne82.documenttree.utils;



public class EulerianDistance {



    public static double calEulerianDistance( Double[] documentVector, Double[] searchVector ) {

        double sum = 0;
        for( int i = 0; i< documentVector.length; i++ ) {
            sum += Math.pow( ( documentVector[i] - searchVector[i]), 2 );
        }
        return Math.sqrt( sum );
    }



    public static double transformEulerianDistanceToRelevanceValue( double eulerianDistance ) {

        // 1/euler -> 1/10=0,1  1/1=1
        if( eulerianDistance == 0 ) {
            return eulerianDistance;
        }
        return ( 1/ eulerianDistance );
    }

}
