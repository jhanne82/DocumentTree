package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import com.github.jhanne82.documenttree.simulation.utils.SimulationSetup;
import com.github.jhanne82.documenttree.tree.DocumentNode;
import com.github.jhanne82.documenttree.tree.DocumentTree;
import com.github.jhanne82.documenttree.utils.ResultDocumentList;
import com.google.common.collect.ImmutableList;
import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DocumentTreeSimulation <T> {


    
    protected DocumentTree<T> documentTreeWithGlobalKnowledge;
    protected DocumentTree<T> documentTreeWithLocalKnowledge;
    protected DocumentTree<T> stressReducedDocumentTree;
    protected Document<T>[] optimalDocumentTree;



    private ResultDocumentList<T> searchOnTree(DocumentTree<T> documentTree, T[] searchTermVector, SearchType searchType, int limitForLocalKnowledge, int searchTimeStamp ) {
        switch ( searchType ) {
            case DEPTH_FIRST:
                return documentTree.depthFirstSearch( limitForLocalKnowledge, searchTermVector, searchTimeStamp );
            case BREADTH_FIRST:
                return documentTree.breadthFirstSearch( limitForLocalKnowledge, searchTermVector, searchTimeStamp );
            case RANDOM_WALKER:
                return documentTree.randomWalkSearch( limitForLocalKnowledge, searchTermVector, searchTimeStamp );
            default:
                throw new UnsupportedOperationException();
        }
    }


    protected abstract Document<T> searchOnOptimalDocumentTree( T[] searchTermVector );


    protected abstract void setupRequiredDocumentTrees( SimulationSetup setup );


    protected abstract T[] createTermVector( Distribution distribution,
                                             int maxCountOfTerms,
                                             int maxCountOfTermsWithQuantifier,
                                             boolean cluster );


    public SimulationResult[] startSearchSimulation( SimulationSetup setup ) {

        SimulationResult resultGlobalKnowledge = new SimulationResult( setup );
        SimulationResult resultLocalKnowledge = new SimulationResult( setup );
        SimulationResult resultStressReducedTree = new SimulationResult( setup );

        for( int i = 0; i < setup.countOfPerformedSearches; i++ ) {

            T[] searchTermVector = createTermVector( setup.distributionForSearchVector,
                                                     setup.countOfTermsUsedToDefineVector,
                                                     setup.countOfTermsWithQuantifier,
                                                     setup.cluster );

            ResultDocumentList<T> resultList;
            int requiredRepositionings;

            Document bestMatch = searchOnOptimalDocumentTree( searchTermVector );

            resultList = searchOnTree( documentTreeWithGlobalKnowledge, searchTermVector, setup.searchType, setup.countOfCreatedDocuments, i+1 );
            requiredRepositionings = documentTreeWithGlobalKnowledge.repositionOfDocuments( setup.requiredSearchesOnDocumentToRespositioning, i+1, setup.treshold );
            storeSimulationResultAfterEverySearch( resultList, bestMatch, requiredRepositionings, resultGlobalKnowledge );

            resultList  = searchOnTree( documentTreeWithLocalKnowledge, searchTermVector, setup.searchType, setup.limitForLocalKnowledge, i+1 );
            requiredRepositionings = documentTreeWithLocalKnowledge.repositionOfDocuments( setup.requiredSearchesOnDocumentToRespositioning, i+1, setup.treshold );
            storeSimulationResultAfterEverySearch( resultList, bestMatch, requiredRepositionings, resultLocalKnowledge );

            resultList = searchOnTree( stressReducedDocumentTree, searchTermVector, setup.searchType, setup.limitForLocalKnowledge, i+1 );
            storeSimulationResultAfterEverySearch( resultList, bestMatch, 0, resultStressReducedTree  );
        }

        List<Document<T>> optimalTreeAsList = generateSortetListFromOptimalTree();

        computeDifferenceBetweenTrees( optimalTreeAsList, documentTreeWithGlobalKnowledge, resultGlobalKnowledge );
        computeDifferenceBetweenTrees( optimalTreeAsList, documentTreeWithLocalKnowledge, resultLocalKnowledge );
        computeDifferenceBetweenTrees( optimalTreeAsList, stressReducedDocumentTree, resultStressReducedTree );
        return new SimulationResult[]{ resultGlobalKnowledge, resultLocalKnowledge, resultStressReducedTree };
    }



    private void storeSimulationResultAfterEverySearch( ResultDocumentList<T> resultList, Document bestMatch, int requiredRepositionings, SimulationResult simulationResult ) {

        calcHitMissRate( resultList.getBestResult(), bestMatch, simulationResult );
        simulationResult.addRequiredSearches(resultList.numberOfSearchesTillOptimum());
        simulationResult.addRequiredRepositioning( requiredRepositionings );
    }



    private void calcHitMissRate( Document foundDocument, Document bestMatch, SimulationResult result ) {

        if( bestMatch.getDocumentName().equals( foundDocument.getDocumentName() ) ) {
            result.setHitRate( result.getHitRate() + 1 );
        } else {
            result.setMissRate( result.getMissRate() + 1 );
        }
    }



    private List<Document<T>> generateSortetListFromOptimalTree() {
        List<Document<T>> optimalTreeAsList = Arrays.asList( optimalDocumentTree );
        optimalTreeAsList.sort( (d1, d2) -> {
            if( d1.getAverageRelevance() == d2.getAverageRelevance() ) {
                return 0;
            }
            if( d1.getAverageRelevance() < d2.getAverageRelevance() ) {
                return 1;
            }
            return -1;
        }  );

        return optimalTreeAsList;

    }



    private void computeDifferenceBetweenTrees( List<Document<T>> treeAsList, DocumentTree<T> tree, SimulationResult result ) {

        int documentsOnCorrectLevel=0;
        int level = 0;

        List<DocumentNode<T>> nodesOnNextLevel = new ArrayList<>();
        nodesOnNextLevel.add( tree.getRootNode() );

        while ( !nodesOnNextLevel.isEmpty() ) {

            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
            nodesOnNextLevel = new ArrayList<>();

            for( DocumentNode<T> node : nodesOnCurrentLevel ) {

                int index = treeAsList.indexOf( node.getDocument() ) +1;

                int optimalLevel = LongMath.log2( index +1, RoundingMode.DOWN );
                if( level == optimalLevel) {
                    documentsOnCorrectLevel++;
                }
                result.addDistanceToOptimalPosition( Math.abs( level - optimalLevel ) );

                nodesOnNextLevel.addAll(node.getChildLeaves());
            }
            level++;
        }
        result.setDocumentOnCorrectLevel( documentsOnCorrectLevel );
    }
}
