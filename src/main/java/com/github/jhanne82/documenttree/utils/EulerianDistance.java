package com.github.jhanne82.documenttree.utils;


import java.math.BigDecimal;

public class EulerianDistance {



    public static<T> BigDecimal calEulerianDistance( T[] vectorA, T[] vectorB ) {

        BigDecimal eulerianDistance = BigDecimal.ZERO;


        return eulerianDistance;
    }



    public static BigDecimal transformEulerianDistanceToRelevanceValue( BigDecimal eulerianDistance ) {
        double basis = 0.5;

        // 1/euler -> 1/10=0,1  1/1=1
        return BigDecimal.ONE.divide( eulerianDistance );
    }

}
