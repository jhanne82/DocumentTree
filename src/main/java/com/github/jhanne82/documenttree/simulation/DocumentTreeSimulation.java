package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.document.Term;

import java.util.List;

public abstract class DocumentTreeSimulation <T> {


    
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


    protected abstract void setupRequiredDocumentTrees( SimulationSetup setup );


    protected abstract List<Term<T>> createTermVector( Distribution distribution,
                                                       int maxCountOfTerms,
                                                       int maxCountOfTermsWithQuantifier );


    public SimulationResult startSearchSimulation( SimulationSetup setup ) {

        SimulationResult result = new SimulationResult( setup );

        for( int i = 0; i < setup.countOfPerformedSearches; i++ ) {
            System.out.println( "Suche " + i );

            List<Term<T>> searchTermVector = createTermVector( setup.distributionForSearchVector,
                                                               setup.countOfTermsUsedToDefineVector,
                                                               setup.countOfTermsWithQuantifier);

            Document bestMatch = searchOnOptimalDocumentTree( searchTermVector );
            Document foundDocument = searchOnTree( documentTreeWithGlobalKnowledge, searchTermVector, setup.searchType, setup.countOfCreatedDocuments );
            searchOnTree( documentTreeWithLocalKnowledge, searchTermVector, setup.searchType, setup.limitForLocalKnowledge );
            searchOnTree( stressReducedDocumentTree, searchTermVector, setup.searchType, setup.limitForLocalKnowledge );

            documentTreeWithLocalKnowledge.repositioning( setup.requiredSearchesOnDocumentToRespositioning );
            documentTreeWithGlobalKnowledge.repositioning( setup.requiredSearchesOnDocumentToRespositioning );

            if( bestMatch.getDocumentName().equals( foundDocument.getDocumentName() ) ) {
                result.setHitRate( result.getHitRate() + 1 );
            } else {
                result.setMissRate( result.getMissRate() + 1 );
            }
        }
        return result;
    }
}
