package com.github.jhanne82.documenttree.simulation.documenttree;

import com.github.jhanne82.documenttree.DocumentTree;
import com.github.jhanne82.documenttree.simulation.documenttree.retrieval.EulerianDistance;


public class NumberDocumentTree
    extends DocumentTree<Double>{

    @Override
    protected double calcRelevanceOfDocument( Double[] documentTermVector, Double[] searchTermVector ) {
        return EulerianDistance.calcRelevance( documentTermVector, searchTermVector );
    }
}
