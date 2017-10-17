package com.github.jhanne82.documenttree.utils;



public class EulerianDistance {



    protected static double calEulerianDistance(Double[] documentVector, Double[] searchVector ) {

        double sum = 0;

        for( int i = 0; i < documentVector.length; i++ ) {
            double documentTermContent = documentVector[i] == null ? 0 : documentVector[i];
            double searchTermContent = searchVector[i] == null ? 0 : searchVector[i];
            sum += Math.pow( documentTermContent - searchTermContent, 2 );
        }

        return Math.sqrt( sum );
    }



    private static double transformEulerianDistanceToRelevanceValue( double eulerianDistance ) {

        // 1/euler -> 1/10=0,1  1/1=1
        if( eulerianDistance == 0 ) {
            return eulerianDistance;
        }
        return ( 1/ eulerianDistance );
    }



    public static double calcRelevance( Double[] documentVector, Double[] searchVector ) {

        double distance = calEulerianDistance( documentVector, searchVector );
        return transformEulerianDistanceToRelevanceValue( distance );
    }

}
