package com.github.jhanne82.documenttree.utils;


import org.jscience.mathematics.number.Real;


public class EulerianDistance {



    protected static Real calEulerianDistance(Double[] documentVector, Double[] searchVector ) {

        Real sum = Real.ZERO;

        for( int i = 0; i < documentVector.length; i++ ) {
            Real documentTermContent = documentVector[i] == null ? Real.ZERO : Real.valueOf(documentVector[i]);
            Real searchTermContent = searchVector[i] == null ? Real.ZERO : Real.valueOf( searchVector[i] );

            sum = sum.plus( documentTermContent.minus( searchTermContent ).pow( 2 ));
        }

        return sum.sqrt();
    }



    private static Real transformEulerianDistanceToRelevanceValue( Real eulerianDistance ) {

        return Real.ONE.divide( eulerianDistance );

    }



    public static Real calcRelevance( Double[] documentVector, Double[] searchVector ) {

        Real distance = calEulerianDistance( documentVector, searchVector );
        return transformEulerianDistanceToRelevanceValue( distance );
    }

}
