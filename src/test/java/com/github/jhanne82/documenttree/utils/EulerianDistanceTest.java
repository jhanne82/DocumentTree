package com.github.jhanne82.documenttree.utils;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class EulerianDistanceTest {


    Double[] documentVector = {0.1, 0.2, 0.3, 0.4, null};
    Double[] searchVector   = {0.5, 0.6, 0.7, 0.8, 0d };


    @Test
    public void calcRelevanceTest() {
        double expectedRel = 1.25;
        double calcRel = EulerianDistance.calcRelevance( documentVector, searchVector );

        assertEquals( expectedRel, calcRel );
    }

}
