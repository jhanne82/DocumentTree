package com.github.jhanne82.documenttree.tree.number;

import com.github.jhanne82.documenttree.tree.DocumentTree;
import com.github.jhanne82.documenttree.utils.EulerianDistance;
import org.jscience.mathematics.number.Real;


public class NumberDocumentTree
    extends DocumentTree<Double>{

    @Override
    protected Real calcRelevanceOfDocument( Double[] documentTermVector, Double[] searchTermVector ) {
        return EulerianDistance.calcRelevance( documentTermVector, searchTermVector );
    }
}
