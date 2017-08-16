package com.github.jhanne82.documenttree.utils;


import com.github.jhanne82.documenttree.document.Term;

import java.util.List;

public class EulerianDistance {



    public static double calEulerianDistance(List<Term<Double>> documentVector, List<Term<Double>> searchVector ) {

        documentVector.sort( (t1, t2 ) -> Integer.compare(t1.getIndex(), t2.getIndex()));
        searchVector.sort( (t1, t2 ) -> Integer.compare(t1.getIndex(), t2.getIndex()));

        int maxIndex = documentVector.get( documentVector.size() -1 ).getIndex();

        if( maxIndex < searchVector.get( searchVector.size() -1 ).getIndex() ) {
            maxIndex = searchVector.get( searchVector.size() -1 ).getIndex();
        }

        double sum = 0;
        for( int i = 0; i < maxIndex; i++ ) {
            int finalI = i;
            Term<Double> documentTerm = documentVector.stream().filter(x -> x.getIndex() == finalI).findFirst().orElse( null );
            Term<Double> searchTerm   = searchVector.stream().filter(x -> x.getIndex() == finalI).findFirst().orElse( null );

            double documentTermContent = documentTerm != null ? documentTerm.getContent() : 0;
            double searchTermContent   = searchTerm != null ? searchTerm.getContent() : 0;

            sum += Math.pow( (documentTermContent - searchTermContent ), 2 );
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
