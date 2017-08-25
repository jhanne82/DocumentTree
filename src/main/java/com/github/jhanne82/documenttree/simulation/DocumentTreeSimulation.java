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



    public void runSimulation( List<List<Term<T>>> searchTermVectorList,
                               SearchType searchType,
                               int limitForLocalKnowledge,
                               int countOfSearchesForRepositioning ) {

        int i = 0;
        for( List<Term<T>> searchTerm : searchTermVectorList ) {
            search( searchTerm, searchType, limitForLocalKnowledge );
            documentTreeWithLocalKnowledge.repositioning( countOfSearchesForRepositioning );
            documentTreeWithGlobalKnowledge.repositioning( countOfSearchesForRepositioning );
        }

        System.out.println( resultOfGlobalKnowledge );
    }

    private void search( List<Term<T>> searchTermVector, SearchType searchType, int limitForLocalKnowledge ) {

        Document bestMatch = searchOnOptimalDocumentTree( searchTermVector );
        switch ( searchType ) {
            case DEPTH_FIRST:
                resultOfGlobalKnowledge.computeHitMissRate( documentTreeWithGlobalKnowledge.depthFirstSearch( 0, searchTermVector ), bestMatch );
                documentTreeWithLocalKnowledge.depthFirstSearch( limitForLocalKnowledge, searchTermVector );
                stressReducedDocumentTree.depthFirstSearch( limitForLocalKnowledge, searchTermVector );
                break;
            case BREADTH_FIRST:
                documentTreeWithGlobalKnowledge.breadthFirstSearch( 0, searchTermVector );
                documentTreeWithLocalKnowledge.breadthFirstSearch( limitForLocalKnowledge, searchTermVector );
                stressReducedDocumentTree.breadthFirstSearch( limitForLocalKnowledge, searchTermVector );
                break;
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
            search(createTermVector( distribution,
                                      maxCountOfTermsUsedToDefineVector,
                                      maxCountOfTermsWithQuantifier),
                    searchType,
                    limitForLocalKnowledge );

            documentTreeWithLocalKnowledge.repositioning( numberOfSearchesBeforeRepositioning );
            documentTreeWithGlobalKnowledge.repositioning( numberOfSearchesBeforeRepositioning );
        }
        return result;
    }
}
