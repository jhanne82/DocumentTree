package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;


public abstract class DocumentTreeSimulation <T> {


    
    protected DocumentTree<T> documentTreeWithGlobalKnowledge;
    protected DocumentTree<T> documentTreeWithLocalKnowledge;
    protected DocumentTree<T> stressReducedDocumentTree;
    protected Document<T>[] optimalDocumentTree;



    private Document<T> searchOnTree( DocumentTree<T> documentTree, T[] searchTermVector, SearchType searchType, int limitForLocalKnowledge ) {

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


    protected abstract Document<T> searchOnOptimalDocumentTree( T[] searchTermVector );


    protected abstract void setupRequiredDocumentTrees( SimulationSetup setup );


    protected abstract T[] createTermVector( Distribution distribution,
                                             int maxCountOfTerms,
                                             int maxCountOfTermsWithQuantifier );


    public SimulationResult[] startSearchSimulation( SimulationSetup setup ) {

        SimulationResult resultGlobalKnowledge = new SimulationResult( setup );
        SimulationResult resultLocalKnowledge = new SimulationResult( setup );

        for( int i = 0; i < setup.countOfPerformedSearches; i++ ) {

            T[] searchTermVector = createTermVector( setup.distributionForSearchVector,
                                                     setup.countOfTermsUsedToDefineVector,
                                                     setup.countOfTermsWithQuantifier);

            Document bestMatch = searchOnOptimalDocumentTree( searchTermVector );
            Document foundDocument = searchOnTree( documentTreeWithGlobalKnowledge, searchTermVector, setup.searchType, setup.countOfCreatedDocuments );
            calcHitMissRate( foundDocument, bestMatch, resultGlobalKnowledge );

            foundDocument  = searchOnTree( documentTreeWithLocalKnowledge, searchTermVector, setup.searchType, setup.limitForLocalKnowledge );
            calcHitMissRate( foundDocument, bestMatch, resultLocalKnowledge );

            searchOnTree( stressReducedDocumentTree, searchTermVector, setup.searchType, setup.limitForLocalKnowledge );

            documentTreeWithLocalKnowledge.repositioning( setup.requiredSearchesOnDocumentToRespositioning );
            documentTreeWithGlobalKnowledge.repositioning( setup.requiredSearchesOnDocumentToRespositioning );
        }

        return new SimulationResult[]{ resultGlobalKnowledge, resultLocalKnowledge };
    }



    private void calcHitMissRate( Document foundDocument, Document bestMatch, SimulationResult result ) {

        if( bestMatch.getDocumentName().equals( foundDocument.getDocumentName() ) ) {
            result.setHitRate( result.getHitRate() + 1 );
        } else {
            result.setMissRate( result.getMissRate() + 1 );
        }
    }
}
