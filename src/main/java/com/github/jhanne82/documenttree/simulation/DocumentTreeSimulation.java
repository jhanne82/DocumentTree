package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.DocumentNode;
import com.github.jhanne82.documenttree.DocumentTree;
import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.configuration.Parameter;
import com.github.jhanne82.documenttree.simulation.documenttree.NumberDocumentTree;
import com.github.jhanne82.documenttree.simulation.documenttree.retrieval.EulerianDistance;
import com.github.jhanne82.documenttree.simulation.enumeration.Distribution;
import com.github.jhanne82.documenttree.simulation.enumeration.SearchType;
import com.github.jhanne82.documenttree.simulation.utils.RandomNumberGenerator;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import com.github.jhanne82.documenttree.simulation.utils.SimulationSetup;
import com.github.jhanne82.documenttree.utils.ResultDocumentList;
import com.google.common.collect.ImmutableList;
import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DocumentTreeSimulation {


    
    protected DocumentTree<Double> documentTreeWithGlobalKnowledge;
    protected DocumentTree<Double> documentTreeWithLocalKnowledge;
    protected DocumentTree<Double> stressReducedDocumentTree;
    protected Document<Double>[] optimalDocumentTree;


    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
    private Map<Double, Double>   clusterMap;


    public SimulationResult[] startSearchSimulation( Parameter parameter ) {

        return startSearchSimulation( new SimulationSetup( parameter.getSearchType(),
                                                           parameter.getDistributionForDocument(),
                                                           parameter.getDistributionForSearch(),
                                                           parameter.getMaxCountOfTermsUsedToDefineVector(),
                                                           parameter.getMaxCountOfTermsWithQuantifier(),
                                                           parameter.getMaxCountOfCreatedDocuments(),
                                                           parameter.getMaxCountOfCreatedSearches(),
                                                           parameter.getLimitForLocalKnowledge(),
                                                           parameter.getNumberOfSearchesBeforeRepositioning(),
                                                           parameter.getTHRESHOLD(),
                                                           parameter.isCluster() ) );

    }


    public SimulationResult[] startSearchSimulation( SimulationSetup setup ) {

        SimulationResult resultGlobalKnowledge = new SimulationResult( setup );
        SimulationResult resultLocalKnowledge = new SimulationResult( setup );
        SimulationResult resultStressReducedTree = new SimulationResult( setup );

        for( int i = 0; i < setup.countOfPerformedSearches; i++ ) {

            Double[] searchTermVector = createTermVector( setup.distributionForSearchVector,
                                                          setup.countOfTermsUsedToDefineVector,
                                                          setup.countOfTermsWithQuantifier,
                                                          setup.cluster );

            Document<Double> bestMatch = searchOnOptimalDocumentTree( searchTermVector );

            List<TreeSearchSimulationThread> threads = Arrays.asList(
                    new TreeSearchSimulationThread( searchTermVector, setup, i + 1, bestMatch, resultGlobalKnowledge, documentTreeWithGlobalKnowledge, false, true ),
                    new TreeSearchSimulationThread( searchTermVector, setup, i + 1, bestMatch, resultLocalKnowledge, documentTreeWithLocalKnowledge, true, true ),
                    new TreeSearchSimulationThread( searchTermVector, setup, i + 1, bestMatch, resultStressReducedTree, stressReducedDocumentTree, true, false ));

            for( Thread thread : threads ) {
                try {
                    thread.start();
                    thread.join();
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }

        stressReducedDocumentTree.repositionOfDocuments( 0, 0, Integer.MAX_VALUE );

        List<Document<Double>> optimalTreeAsList = generateSortedListFromOptimalTree();
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

        return new SimulationResult[]{ resultGlobalKnowledge, resultLocalKnowledge, resultStressReducedTree };
    }



    private List<Document<Double>> generateSortedListFromOptimalTree() {
        List<Document<Double>> optimalTreeAsList = Arrays.asList( optimalDocumentTree );
        optimalTreeAsList.sort( Comparator.comparing( Document::getAverageRelevance ) );

        return optimalTreeAsList;

    }



    private class TreeSearchSimulationThread
        extends Thread {


        private final Double[]              searchTermVector;
        private final SimulationSetup  setup;
        private final int              searchTimeStamp;
        private final Document<Double>      bestMatch;
        private final SimulationResult result;
        private final DocumentTree<Double>  tree;
        private final boolean isLocalKnowledge;
        private final boolean shouldRepositionAfterSearch;

        TreeSearchSimulationThread( Double[] searchTermVector, SimulationSetup setup, int searchTimeStamp, Document<Double> bestMatch, SimulationResult result, DocumentTree<Double> tree, boolean isLocalKnowledge, boolean shouldRepositionAfterSearch ) {
            this.searchTermVector = searchTermVector;
            this.setup = setup;
            this.searchTimeStamp = searchTimeStamp +1;
            this.bestMatch = bestMatch;
            this.result = result;
            this.tree = tree;
            this.isLocalKnowledge = isLocalKnowledge;
            this.shouldRepositionAfterSearch = shouldRepositionAfterSearch;
        }



        @Override
        public void run() {
            int limitForLocalKnowledge = setup.countOfCreatedDocuments;
            if( isLocalKnowledge ) {
                limitForLocalKnowledge = setup.limitForLocalKnowledge;
            }
            ResultDocumentList resultList = searchOnTree( tree, searchTermVector, setup.searchType, limitForLocalKnowledge, searchTimeStamp );
            int requiredRepositionings = 0;
            if(shouldRepositionAfterSearch) {
                requiredRepositionings = tree.repositionOfDocuments( setup.requiredSearchesOnDocumentToRespositioning, searchTimeStamp, setup.treshold );
            }
            storeSimulationResultAfterEverySearch( resultList, bestMatch, requiredRepositionings, result );
        }



        private ResultDocumentList<Double> searchOnTree( DocumentTree<Double> documentTree, Double[] searchTermVector, SearchType searchType, int limitForLocalKnowledge, int searchTimeStamp ) {


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


        private void storeSimulationResultAfterEverySearch( ResultDocumentList<Double> resultList, Document bestMatch, int requiredRepositionings, SimulationResult simulationResult ) {

            calcHitMissRate( resultList.getBestResult(), bestMatch, simulationResult );
            simulationResult.addRequiredSearches(resultList.numberOfSearchesTillOptimum());
            simulationResult.addRequiredRepositioning( requiredRepositionings );
            simulationResult.addRequiredNodes( resultList.numberOfVisitedNodes() );
        }


        private void calcHitMissRate( Document foundDocument, Document bestMatch, SimulationResult result ) {

            if(   bestMatch.getDocumentName().equals( foundDocument.getDocumentName() )
               || bestMatch.getLatestCalculatedRelevance() == foundDocument.getLatestCalculatedRelevance()      ) {
                result.setHitRate( result.getHitRate() + 1 );
            } else {
                result.setMissRate( result.getMissRate() + 1 );
            }
        }
    }



    private class CheckDifferencesThread
        extends Thread {


        private List<Document<Double>> sortedList;
        private DocumentTree<Double> tree;
        private SimulationResult result;


        CheckDifferencesThread( List<Document<Double>> sortedList, DocumentTree<Double> tree, SimulationResult result ) {
            this.sortedList = sortedList;
            this.tree = tree;
            this.result = result;
        }


        @Override
        public void run() {
            computeDifferenceBetweenTrees( sortedList, tree, result );

        }


        private void computeDifferenceBetweenTrees( List<Document<Double>> treeAsList, DocumentTree<Double> tree, SimulationResult result ) {

            int documentsOnCorrectLevel=0;
            int level = 0;

            List<DocumentNode<Double>> nodesOnNextLevel = new ArrayList<>();
            nodesOnNextLevel.add( tree.getRootNode() );

            while ( !nodesOnNextLevel.isEmpty() ) {

                List<DocumentNode<Double>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
                nodesOnNextLevel = new ArrayList<>();

                for( DocumentNode<Double> node : nodesOnCurrentLevel ) {

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


    private class CreateTreeThread
            extends Thread {

        DocumentTree<Double> tree;
        Document<Double>[] documents;
        SimulationSetup setup;

        CreateTreeThread( DocumentTree<Double> tree, Document<Double>[] documents, SimulationSetup setup ) {
            this.tree = tree;
            this.documents = documents;
            this.setup = setup;
        }


        @Override
        public void run() {
            tree.level_order_insert( null, documents, 0, setup.countOfCreatedDocuments );
        }
    }


    protected Document<Double> searchOnOptimalDocumentTree( Double[] searchTermVector ) {

        Document<Double> bestMatch = null;

        for ( Document<Double> document : optimalDocumentTree ) {

            document.addRelevance( EulerianDistance.calcRelevance( document.getTermVector(), searchTermVector ) );

            if(    bestMatch == null
                   || document.getLatestCalculatedRelevance() > bestMatch.getLatestCalculatedRelevance() ) {
                bestMatch = document;
            }
        }

        return bestMatch;
    }


    protected void setupRequiredDocumentTrees( Parameter parameter ) {
        setupRequiredDocumentTrees( new SimulationSetup( parameter.getSearchType(),
                                                         parameter.getDistributionForDocument(),
                                                         parameter.getDistributionForSearch(),
                                                         parameter.getMaxCountOfTermsUsedToDefineVector(),
                                                         parameter.getMaxCountOfTermsWithQuantifier(),
                                                         parameter.getMaxCountOfCreatedDocuments(),
                                                         parameter.getMaxCountOfCreatedSearches(),
                                                         parameter.getLimitForLocalKnowledge(),
                                                         parameter.getNumberOfSearchesBeforeRepositioning(),
                                                         parameter.getTHRESHOLD(),
                                                         parameter.isCluster() ) );
    }



    protected void setupRequiredDocumentTrees(SimulationSetup setup ) {

        documentTreeWithGlobalKnowledge = new NumberDocumentTree();
        documentTreeWithLocalKnowledge = new NumberDocumentTree();
        stressReducedDocumentTree = new NumberDocumentTree();

        optimalDocumentTree = createDocuments( setup.distributionForDocumentVector,
                                               setup.countOfTermsUsedToDefineVector,
                                               setup.countOfTermsWithQuantifier,
                                               setup.countOfCreatedDocuments,
                                               setup.cluster);

        List<Thread> threads = Arrays.asList(
                new CreateTreeThread( documentTreeWithGlobalKnowledge, optimalDocumentTree, setup ),
                new CreateTreeThread( documentTreeWithLocalKnowledge,  optimalDocumentTree, setup),
                new CreateTreeThread( stressReducedDocumentTree,       optimalDocumentTree, setup )
        );

        for( Thread thread : threads ) {
            try {
                thread.start();
                thread.join();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }



    protected Double[] createTermVector( Distribution distribution,
                                         int maxCountOfTerms,
                                         int maxCountOfTermsWithQuantifier,
                                         boolean cluster ) {

        if( cluster && clusterMap == null ) {
            createClusterMap();
        }

        Double[] termList = new Double[maxCountOfTerms];

        for( int termsWithQuantifier = 0; termsWithQuantifier < maxCountOfTermsWithQuantifier; ) {
            int index = randomNumberGenerator.getInt( maxCountOfTerms );

            if( termList[index] == null || termList[index] == 0 ) {
                double term = randomNumberGenerator.getDouble(distribution, 10 );
                if( cluster && clusterMap.containsKey( term ) ) {
                    term = clusterMap.get( term );
                }
                termList[index] = term;
                termsWithQuantifier++;
            }
        }

        return termList;
    }


    private void createClusterMap() {
        clusterMap = new HashMap<>();

        for( int i = 0; i< 3; i++ ) {
            double bunch = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );

            for (int j = 0; j < 3; j++ ) {
                double singleItem = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );
                while( singleItem == bunch ) {
                    singleItem = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );
                }
                clusterMap.put( singleItem, bunch );
            }
        }
    }



    private Document<Double>[] createDocuments( Distribution distribution,
                                                int maxCountOfTerms,
                                                int maxCountOfTermsWithQuantifier,
                                                int maxCountOfDocuments,
                                                boolean cluster ) {
        Document<Double>[] documentArray = new Document[maxCountOfDocuments];

        for( int i = 0; i < maxCountOfDocuments; i++ ) {
            documentArray[i] = new Document<>( createTermVector( distribution,
                                                                 maxCountOfTerms,
                                                                 maxCountOfTermsWithQuantifier,
                                                                 cluster ),
                                               "Document " + i );
        }

        return documentArray;
    }
}
