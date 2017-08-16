package com.github.jhanne82.documenttree.utils;



public class EulerianDistance {



    public static double calEulerianDistance( Double[] documentVector, Double[] searchVector ) {

        double sum = 0;
        for( int i = 0; i< documentVector.length; i++ ) {
            double documentTerm = ( documentVector[i] == null ? 0 : documentVector[i] );
            double searchTerm   = ( searchVector[i] == null ? 0 : searchVector[i] );
            sum += Math.pow( ( documentTerm - searchTerm ), 2 );
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
