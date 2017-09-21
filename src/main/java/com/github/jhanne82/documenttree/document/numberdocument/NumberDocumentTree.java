package com.github.jhanne82.documenttree.document.numberdocument;

import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.utils.EulerianDistance;



public class NumberDocumentTree
    extends DocumentTree<Double>{

    @Override
    protected double calcRelevanceOfDocument( Double[] documentTermVector, Double[] searchTermVector ) {
        return EulerianDistance.calcRelevance( documentTermVector, searchTermVector );
    }
}
