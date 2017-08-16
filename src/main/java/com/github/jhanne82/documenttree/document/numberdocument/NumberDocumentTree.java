package com.github.jhanne82.documenttree.document.numberdocument;

import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.document.Term;
import com.github.jhanne82.documenttree.utils.EulerianDistance;

import java.util.List;


public class NumberDocumentTree
    extends DocumentTree<Double>{

    @Override
    protected double calcRelevanceOfDocument( List<Term<Double>> documentTermVector, List<Term<Double>> searchTermVector ) {
        double eulerianDistance = EulerianDistance.calEulerianDistance( documentTermVector, searchTermVector );
        return EulerianDistance.transformEulerianDistanceToRelevanceValue( eulerianDistance );
    }
}
