package com.github.jhanne82.documenttree.simulation.documenttree;

import com.github.jhanne82.documenttree.DocumentTree;
import com.github.jhanne82.documenttree.simulation.documenttree.retrieval.EulerianDistance;


/**
 * Specific implementation of {@link com.github.jhanne82.documenttree.DocumentTree}.
 * The Documents which are stored within the tree are defines by term vector of type {@see Double}
 */
public class NumberDocumentTree
    extends DocumentTree<Double>{



    @Override
    /**
     * Calculates the relevance of the document by the given search term.
     *
     * @param documentTermVector defines the vetor of the term
     * @param searchTermVector defines the term vector of the search.
     * @return the calculated relevance
     */
    protected double calcRelevanceOfDocument( Double[] documentTermVector, Double[] searchTermVector ) {
        return EulerianDistance.calcRelevance( documentTermVector, searchTermVector );
    }
}
