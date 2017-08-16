package com.github.jhanne82.documenttree.simulation.numberdocument;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.DocumentTreeSimulation;
import com.github.jhanne82.documenttree.utils.EulerianDistance;

public class NumberDocumentTreeSimulation
    extends DocumentTreeSimulation <Double> {


    @Override
    protected Document<Double> searchOnOptimalDocumentTree( Double[] searchTermVector ) {

        Document<Double> bestMatch = null;
        double relevanceOfBestMatch = 0;

        for ( Document<Double> document : getOptimalDocumentTree() ) {

            double euleriaDistance = EulerianDistance.calEulerianDistance( document.getTermVector(), searchTermVector );
            double relevance = EulerianDistance.transformEulerianDistanceToRelevanceValue( euleriaDistance );
            document.addRelevance( relevance );

            if( bestMatch == null || relevance > relevanceOfBestMatch ) {
                bestMatch = document;
                relevanceOfBestMatch = relevance;
            }
        }

        return bestMatch;
    }
}
