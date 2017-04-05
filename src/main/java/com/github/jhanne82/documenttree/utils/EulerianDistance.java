package com.github.jhanne82.documenttree.utils;


import java.math.BigDecimal;

public class EulerianDistance {



    public static BigDecimal calEulerianDistance( int[] vectorA, int[] vectorB ) {

        BigDecimal eulerianDistance = BigDecimal.ZERO;


        return eulerianDistance;
    }



    public static BigDecimal transformEulerianDistanceToRelevanceValue( BigDecimal eulerianDistance ) {
        double basis = 0.5;

        return BigDecimal.valueOf( Math.pow( basis, eulerianDistance.doubleValue() ) );
    }

}
