package com.github.jhanne82.documenttree.simulation.documenttree.retrieval;



/**
 * Implementation to compute a relevance between a document and a search vector. To calculate the
 * relevance the eulerian distance will be used.
 */
public class EulerianDistance {


    // calculate euler distance between document and search vector
    // see https://en.wikipedia.org/wiki/Euclidean_distance#Definition
    private static double calEulerianDistance(Double[] documentVector, Double[] searchVector ) {

        double sum = 0;

        for( int i = 0; i < documentVector.length; i++ ) {
            double documentTermContent = documentVector[i] == null ? 0 : documentVector[i];
            double searchTermContent = searchVector[i] == null ? 0 : searchVector[i];
            sum += Math.pow( documentTermContent - searchTermContent, 2 );
        }

        return Math.sqrt( sum );
    }



    // transform the the distance value into relevance value
    // the smaller the distance is, the more relevance is the document
    private static double transformEulerianDistanceToRelevanceValue( double eulerianDistance ) {

        // 1/euler -> 1/10=0,1  1/1=1
        if( eulerianDistance == 0 ) {
            return eulerianDistance;
        }
        return ( 1/ eulerianDistance );
    }



    /**
     * This method will calculate the relevance of a given document for a given search. The relevance calculation
     * is using the euler distance.
     * @param documentVector is the {@code Double} array representing the document vector
     * @param searchVector is the {@code Double} array representing the search vector
     * @return return the calculate relevance from document and search
     */
    public static double calcRelevance( Double[] documentVector, Double[] searchVector ) {

        double distance = calEulerianDistance( documentVector, searchVector );
        return transformEulerianDistanceToRelevanceValue( distance );
    }

}
