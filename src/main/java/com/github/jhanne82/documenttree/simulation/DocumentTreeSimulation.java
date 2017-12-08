package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.enumeration.Distribution;
import com.github.jhanne82.documenttree.enumeration.SearchType;
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
import java.util.Comparator;
import java.util.List;


public abstract class DocumentTreeSimulation <T> {


    
    protected DocumentTree<T> documentTreeWithGlobalKnowledge;
    protected DocumentTree<T> documentTreeWithLocalKnowledge;
    protected DocumentTree<T> stressReducedDocumentTree;
    protected Document<T>[] optimalDocumentTree;



//    private ResultDocumentList<T> searchOnTree(DocumentTree<T> documentTree, T[] searchTermVector, SearchType searchType, int limitForLocalKnowledge, int searchTimeStamp ) {
//        switch ( searchType ) {
//            case DEPTH_FIRST:
//                return documentTree.depthFirstSearch( limitForLocalKnowledge, searchTermVector, searchTimeStamp );
//            case BREADTH_FIRST:
//                return documentTree.breadthFirstSearch( limitForLocalKnowledge, searchTermVector, searchTimeStamp );
//            case RANDOM_WALKER:
//                return documentTree.randomWalkSearch( limitForLocalKnowledge, searchTermVector, searchTimeStamp );
//            default:
//                throw new UnsupportedOperationException();
//        }
//    }


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

//            ResultDocumentList<T> resultList;
//            int requiredRepositionings;

            Document<T> bestMatch = searchOnOptimalDocumentTree( searchTermVector );


            List<TreeSearchSimulationThread> threads = Arrays.asList(
                    new TreeSearchSimulationThread( searchTermVector, setup, i + 1, bestMatch, resultGlobalKnowledge, documentTreeWithGlobalKnowledge, false ),
                    new TreeSearchSimulationThread( searchTermVector, setup, i + 1, bestMatch, resultLocalKnowledge, documentTreeWithLocalKnowledge, true ),
                    new TreeSearchSimulationThread( searchTermVector, setup, i + 1, bestMatch, resultStressReducedTree, stressReducedDocumentTree, true ));

            for( Thread thread : threads ) {
                try {
                    thread.start();
                    thread.join();
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }


//            resultList = searchOnTree( documentTreeWithGlobalKnowledge, searchTermVector, setup.searchType, setup.countOfCreatedDocuments, i+1 );
//            requiredRepositionings = documentTreeWithGlobalKnowledge.repositionOfDocuments( setup.requiredSearchesOnDocumentToRespositioning, i+1, setup.treshold );
//            storeSimulationResultAfterEverySearch( resultList, bestMatch, requiredRepositionings, resultGlobalKnowledge );
//
//            resultList  = searchOnTree( documentTreeWithLocalKnowledge, searchTermVector, setup.searchType, setup.limitForLocalKnowledge, i+1 );
//            requiredRepositionings = documentTreeWithLocalKnowledge.repositionOfDocuments( setup.requiredSearchesOnDocumentToRespositioning, i+1, setup.treshold );
//            storeSimulationResultAfterEverySearch( resultList, bestMatch, requiredRepositionings, resultLocalKnowledge );
//
//            resultList = searchOnTree( stressReducedDocumentTree, searchTermVector, setup.searchType, setup.limitForLocalKnowledge, i+1 );
//            storeSimulationResultAfterEverySearch( resultList, bestMatch, 0, resultStressReducedTree  );
        }


        List<Document<T>> optimalTreeAsList = generateSortedListFromOptimalTree();
        List<CheckDifferencesThread> threads = Arrays.asList(
                new CheckDifferencesThread( optimalTreeAsList, documentTreeWithGlobalKnowledge, resultGlobalKnowledge ),
                new CheckDifferencesThread( optimalTreeAsList, documentTreeWithLocalKnowledge, resultLocalKnowledge ),
                new CheckDifferencesThread( optimalTreeAsList, stressReducedDocumentTree, resultStressReducedTree ) );

        for( Thread thread : threads ) {
            try {
                thread.start();
                thread.join();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }

//        computeDifferenceBetweenTrees( optimalTreeAsList, documentTreeWithGlobalKnowledge, resultGlobalKnowledge );
//        computeDifferenceBetweenTrees( optimalTreeAsList, documentTreeWithLocalKnowledge, resultLocalKnowledge );
//        computeDifferenceBetweenTrees( optimalTreeAsList, stressReducedDocumentTree, resultStressReducedTree );
        return new SimulationResult[]{ resultGlobalKnowledge, resultLocalKnowledge, resultStressReducedTree };
    }



//    private synchronized void storeSimulationResultAfterEverySearch( ResultDocumentList<T> resultList, Document bestMatch, int requiredRepositionings, SimulationResult simulationResult ) {
//
//        calcHitMissRate( resultList.getBestResult(), bestMatch, simulationResult );
//        simulationResult.addRequiredSearches(resultList.numberOfSearchesTillOptimum());
//        simulationResult.addRequiredRepositioning( requiredRepositionings );
//    }



//    private synchronized void calcHitMissRate( Document foundDocument, Document bestMatch, SimulationResult result ) {
//
//        if( bestMatch.getDocumentName().equals( foundDocument.getDocumentName() ) ) {
//            result.setHitRate( result.getHitRate() + 1 );
//        } else {
//            result.setMissRate( result.getMissRate() + 1 );
//        }
//    }



    private List<Document<T>> generateSortedListFromOptimalTree() {
        List<Document<T>> optimalTreeAsList = Arrays.asList( optimalDocumentTree );
        optimalTreeAsList.sort( Comparator.comparing( Document::getAverageRelevance ) );

        return optimalTreeAsList;

    }



//    private synchronized void computeDifferenceBetweenTrees( List<Document<T>> treeAsList, DocumentTree<T> tree, SimulationResult result ) {
//
//        int documentsOnCorrectLevel=0;
//        int level = 0;
//
//        List<DocumentNode<T>> nodesOnNextLevel = new ArrayList<>();
//        nodesOnNextLevel.add( tree.getRootNode() );
//
//        while ( !nodesOnNextLevel.isEmpty() ) {
//
//            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
//            nodesOnNextLevel = new ArrayList<>();
//
//            for( DocumentNode<T> node : nodesOnCurrentLevel ) {
//
//                int index = treeAsList.indexOf( node.getDocument() ) +1;
//
//                int optimalLevel = LongMath.log2( index +1, RoundingMode.DOWN );
//                if( level == optimalLevel) {
//                    documentsOnCorrectLevel++;
//                }
//                result.addDistanceToOptimalPosition( Math.abs( level - optimalLevel ) );
//
//                nodesOnNextLevel.addAll(node.getChildLeaves());
//            }
//            level++;
//        }
//        result.setDocumentOnCorrectLevel( documentsOnCorrectLevel );
//    }



    private class TreeSearchSimulationThread
        extends Thread {


        private final T[]              searchTermVector;
        private final SimulationSetup  setup;
        private final int              searchTimeStamp;
        private final Document<T>      bestMatch;
        private final SimulationResult result;
        private final DocumentTree<T>  tree;
        private final boolean isLocalKnowledge;

        TreeSearchSimulationThread( T[] searchTermVector, SimulationSetup setup, int searchTimeStamp, Document<T> bestMatch, SimulationResult result, DocumentTree<T> tree, boolean isLocalKnowledge ) {
            this.searchTermVector = searchTermVector;
            this.setup = setup;
            this.searchTimeStamp = searchTimeStamp +1;
            this.bestMatch = bestMatch;
            this.result = result;
            this.tree = tree;
            this.isLocalKnowledge = isLocalKnowledge;
        }



        @Override
        public void run() {
            int limitForLocalKnowledge = setup.countOfCreatedDocuments;
            if( isLocalKnowledge ) {
                limitForLocalKnowledge = setup.limitForLocalKnowledge;
            }
            ResultDocumentList resultList = searchOnTree( tree, searchTermVector, setup.searchType, limitForLocalKnowledge, searchTimeStamp );
            int requiredRepositionings = tree.repositionOfDocuments( setup.requiredSearchesOnDocumentToRespositioning, searchTimeStamp, setup.treshold );
            storeSimulationResultAfterEverySearch( resultList, bestMatch, requiredRepositionings, result );
        }



        private ResultDocumentList<T> searchOnTree( DocumentTree<T> documentTree, T[] searchTermVector, SearchType searchType, int limitForLocalKnowledge, int searchTimeStamp ) {


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


        private void storeSimulationResultAfterEverySearch( ResultDocumentList<T> resultList, Document bestMatch, int requiredRepositionings, SimulationResult simulationResult ) {

            calcHitMissRate( resultList.getBestResult(), bestMatch, simulationResult );
            simulationResult.addRequiredSearches(resultList.numberOfSearchesTillOptimum());
            simulationResult.addRequiredRepositioning( requiredRepositionings );
            simulationResult.addRequiredNodes( resultList.numberOfVisitedNodes() );
        }


        private void calcHitMissRate( Document foundDocument, Document bestMatch, SimulationResult result ) {

            if(   bestMatch.getDocumentName().equals( foundDocument.getDocumentName() )
               || bestMatch.getLastCalculatedRelevance() == foundDocument.getLastCalculatedRelevance()      ) {
                result.setHitRate( result.getHitRate() + 1 );
            } else {
                result.setMissRate( result.getMissRate() + 1 );
            }
        }
    }



    private class CheckDifferencesThread
        extends Thread {


        private List<Document<T>> sortedList;
        private DocumentTree<T> tree;
        private SimulationResult result;


        CheckDifferencesThread( List<Document<T>> sortedList, DocumentTree<T> tree, SimulationResult result ) {
            this.sortedList = sortedList;
            this.tree = tree;
            this.result = result;
        }


        @Override
        public void run() {
            computeDifferenceBetweenTrees( sortedList, tree, result );

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
}
