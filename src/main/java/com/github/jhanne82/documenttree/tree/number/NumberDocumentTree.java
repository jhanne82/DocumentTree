package com.github.jhanne82.documenttree.tree.number;

import com.github.jhanne82.documenttree.tree.DocumentTree;
import com.github.jhanne82.documenttree.utils.EulerianDistance;


public class NumberDocumentTree
    extends DocumentTree<Double>{

    @Override
    protected double calcRelevanceOfDocument( Double[] documentTermVector, Double[] searchTermVector ) {
        return EulerianDistance.calcRelevance( documentTermVector, searchTermVector );
    }
}
