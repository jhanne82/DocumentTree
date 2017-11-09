package com.github.jhanne82.documenttree.utils;

import org.jscience.mathematics.number.Real;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class EulerianDistanceTest {


    Double[] documentVector = {0.1, 0.2, 0.3, 0.4, null};
    Double[] searchVector   = {0.5, 0.6, 0.7, 0.8, 0d };


    @Test
    @Ignore
    public void calcEulerianDistance() {

        Real expectedDist = Real.valueOf( 0.8 );
        Real calcDistance = EulerianDistance.calEulerianDistance( documentVector, searchVector );

        assertTrue( expectedDist.compareTo( calcDistance ) == 0 );
    }


    @Test
    public void calcRelevanceTest() {
        Real expectedRel = Real.valueOf(1.25);

        Real calcRel = EulerianDistance.calcRelevance( documentVector, searchVector );

        assertTrue( expectedRel.compareTo( calcRel ) == 0 );
    }

}
