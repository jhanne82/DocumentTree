package com.github.jhanne82.documenttree.simulation.numberdocument;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.DocumentTreeSimulation;
import com.github.jhanne82.documenttree.utils.EulerianDistance;

public class NumberDocumentTreeSimulation
    extends DocumentTreeSimulation <Double> {


    @Override
    protected void searchOnOptimalDocumentTree( Double[] searchTermVector ) {

        for ( Document<Double> document : getOptimalDocumentTree() ) {

            double euleriaDistance = EulerianDistance.calEulerianDistance( document.getTermVector(), searchTermVector );
            document.addRelevance( EulerianDistance.transformEulerianDistanceToRelevanceValue( euleriaDistance ) );
        }
    }
}
