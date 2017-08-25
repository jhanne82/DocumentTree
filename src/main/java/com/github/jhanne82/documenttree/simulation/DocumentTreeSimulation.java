package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.document.Term;

import java.util.List;

public abstract class DocumentTreeSimulation <T> {


    
    private SimulationResult resultOfGlobalKnowledge = new SimulationResult();
    private SimulationResult resultOfLocalKnowledge = new SimulationResult();


    protected DocumentTree<T> documentTreeWithGlobalKnowledge;
    protected DocumentTree<T> documentTreeWithLocalKnowledge;
    protected DocumentTree<T> stressReducedDocumentTree;
    protected Document<T>[] optimalDocumentTree;



    private Document<T> searchOnTree( DocumentTree<T> documentTree, List<Term<T>> searchTermVector, SearchType searchType, int limitForLocalKnowledge ) {

        switch ( searchType ) {
            case DEPTH_FIRST:
                return documentTree.depthFirstSearch( limitForLocalKnowledge, searchTermVector ).get(0);
            case BREADTH_FIRST:
                return documentTree.breadthFirstSearch( limitForLocalKnowledge, searchTermVector ).get(0);
            case RANDOM_WALKER:
            default:
                throw new UnsupportedOperationException();
        }
    }


    protected abstract Document<T> searchOnOptimalDocumentTree( List<Term<T>> searchTermVector );


    protected abstract void setupRequiredDocumentTrees( Distribution distributionOfDocumentTerms,
                                                        int maxCountOfTerms,
                                                        int maxCountOfTermsWithQuantifier,
                                                        int maxCountOfDocuments,
                                                        boolean cluster );


    protected abstract List<Term<T>> createTermVector( Distribution distribution,
                                                       int maxCountOfTerms,
                                                       int maxCountOfTermsWithQuantifier );


    public SimulationResult startSearchSimulation(Distribution distribution,
                                                  SearchType searchType,
                                                  int maxCountOfTermsUsedToDefineVector,
                                                  int maxCountOfTermsWithQuantifier,
                                                  int maxCountOfCreatedSearches,
                                                  int limitForLocalKnowledge,
                                                  int numberOfSearchesBeforeRepositioning) {

        SimulationResult result = new SimulationResult();

        for( int i = 0; i < maxCountOfCreatedSearches; i++ ) {
            System.out.println( "Suche " + i );

            List<Term<T>> searchTermVector = createTermVector( distribution,
                                                               maxCountOfTermsUsedToDefineVector,
                                                               maxCountOfTermsWithQuantifier);

            Document bestMatch = searchOnOptimalDocumentTree( searchTermVector );
            searchOnTree( documentTreeWithGlobalKnowledge, searchTermVector, searchType, 1000 );
            searchOnTree( documentTreeWithLocalKnowledge, searchTermVector, searchType, limitForLocalKnowledge );
            searchOnTree( stressReducedDocumentTree, searchTermVector, searchType, limitForLocalKnowledge );

            documentTreeWithLocalKnowledge.repositioning( numberOfSearchesBeforeRepositioning );
            documentTreeWithGlobalKnowledge.repositioning( numberOfSearchesBeforeRepositioning );
        }
        return result;
    }
}
