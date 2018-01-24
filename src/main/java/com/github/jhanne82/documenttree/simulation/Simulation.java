package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.DocumentNode;
import com.github.jhanne82.documenttree.DocumentTree;
import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentList;
import com.github.jhanne82.documenttree.simulation.configuration.Configuration;
import com.github.jhanne82.documenttree.simulation.configuration.Setup;
import com.github.jhanne82.documenttree.simulation.documenttree.NumberDocumentTree;
import com.github.jhanne82.documenttree.simulation.documenttree.retrieval.EulerianDistance;
import com.github.jhanne82.documenttree.simulation.utils.RandomNumberGenerator;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import com.github.jhanne82.documenttree.simulation.utils.Utility;
import com.google.common.collect.ImmutableList;
import com.google.common.math.LongMath;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Simulation {



    private static final int SEARCH_MODE_GLOBAL = 0;
    private static final int SEARCH_MODE_LOCAL  = 1;
    private static final int SEARCH_MODE_STRESS_REDUCED = 2;


    private NumberDocumentTree documentTreeWithGlobalKnowledge;
    private NumberDocumentTree documentTreeWithLocalKnowledge;
    private NumberDocumentTree stressReducedDocumentTree;
    private Document<Double>[] optimalDocumentTree;



    public SimulationResult[] start( Configuration parameter ) {
        Map<Double, Double> clusterMap = null;
        if( parameter.isCluster() ) {
            clusterMap = Utility.createClusterMap();
        }
        setupDocumentTress( parameter, clusterMap );
        return simulateSearchesOnTrees( parameter, clusterMap );
    }



    private SimulationResult[] simulateSearchesOnTrees( Configuration parameter, Map<Double,Double> clusterMap ) {
        RandomNumberGenerator numberGenerator = new RandomNumberGenerator();

        SimulationResult resultGlobalKnowledge = new SimulationResult( parameter );
        SimulationResult resultLocalKnowledge = new SimulationResult( parameter );
        SimulationResult resultStressReducedTree = new SimulationResult( parameter );

        for( int i = 0; i < parameter.getMaxCountOfCreatedSearches(); i++ ) {
            Double[] searchTermVector = Utility.createTermVector( parameter.getDistributionForSearch(),
                                                                  parameter.getMaxCountOfTermsUsedToDefineVector(),
                                                                  parameter.getMaxCountOfTermsWithQuantifier(),
                                                                  parameter.isCluster(),
                                                                  clusterMap,
                                                                  numberGenerator );

            Document<Double> bestMatch = searchOnOptimalDocumentTree( searchTermVector );
            computeSimulationResult( searchOnTree( parameter,
                                                   documentTreeWithGlobalKnowledge,
                                                   searchTermVector, i+1, false),
                                     bestMatch,
                                     documentTreeWithGlobalKnowledge.repositionOfDocuments( parameter.getNumberOfSearchesBeforeRepositioning(),
                                                                                            i+1,
                                                                                            parameter.getThreshold() ),
                                     resultGlobalKnowledge );

            computeSimulationResult( searchOnTree( parameter,
                                                   documentTreeWithLocalKnowledge,
                                                   searchTermVector, i+1, true),
                                     bestMatch,
                                     documentTreeWithLocalKnowledge.repositionOfDocuments( parameter.getNumberOfSearchesBeforeRepositioning(),
                                                                                            i+1,
                                                                                            parameter.getThreshold() ),
                                     resultLocalKnowledge );

            computeSimulationResult( searchOnTree( parameter,
                                                   stressReducedDocumentTree,
                                                   searchTermVector, i+1, true),
                                      bestMatch, 0, resultStressReducedTree );


        }

        stressReducedDocumentTree.repositionOfDocuments( 0, 0, Integer.MAX_VALUE );

        List<Document<Double>> optimalTreeAsList = Arrays.asList( optimalDocumentTree );
        optimalTreeAsList.sort( Comparator.comparing( Document::getAverageRelevance ) );

        computeDifferenceBetweenTrees( optimalTreeAsList, documentTreeWithGlobalKnowledge, resultGlobalKnowledge );
        computeDifferenceBetweenTrees( optimalTreeAsList, documentTreeWithLocalKnowledge,  resultLocalKnowledge );
        computeDifferenceBetweenTrees( optimalTreeAsList, stressReducedDocumentTree,       resultStressReducedTree );

        return new SimulationResult[]{ resultGlobalKnowledge, resultLocalKnowledge, resultStressReducedTree };
    }


    private DocumentList<Double> searchOnTree(Configuration parameter,
                                              NumberDocumentTree tree,
                                              Double[] searchTermVector,
                                              int searchTimeStamp,
                                              boolean isLimitedKnowledge  ) {

        switch ( parameter.getSearchType() ) {
            case DEPTH_FIRST:
                return tree.depthFirstSearch( isLimitedKnowledge ? parameter.getLimitForLocalKnowledge()
                                                                 : parameter.getMaxCountOfCreatedDocuments(),
                                              searchTermVector, searchTimeStamp );
            case BREADTH_FIRST:
                return tree.breadthFirstSearch( isLimitedKnowledge ? parameter.getLimitForLocalKnowledge()
                                                                   : parameter.getMaxCountOfCreatedDocuments(),
                                                searchTermVector, searchTimeStamp );
            case RANDOM_WALKER:
                return tree.randomWalkSearch( isLimitedKnowledge ? parameter.getLimitForLocalKnowledge()
                                                                 : parameter.getMaxCountOfCreatedDocuments(),
                                              searchTermVector, searchTimeStamp );
            default:
                throw new UnsupportedOperationException();
        }
    }



    private void computeSimulationResult( DocumentList<Double> resultDocumentList,
                                          Document<Double> bestMatch,
                                          int requiredRepositionings,
                                          SimulationResult result ) {
        Utility.calcHitMissRate( resultDocumentList.getBestResult(), bestMatch, result );
        result.addRequiredSearches(resultDocumentList.numberOfSearchesTillOptimum());
        result.addRequiredRepositioning( requiredRepositionings );
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

                nodesOnNextLevel.addAll( node.getChildLeaves() );
            }
            level++;
        }
        result.setDocumentOnCorrectLevel( documentsOnCorrectLevel );
    }



    private Document<Double> searchOnOptimalDocumentTree( Double[] searchTermVector ) {

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



    private void setupDocumentTress( Configuration parameter, Map<Double, Double> clusterMap ) {
        documentTreeWithGlobalKnowledge = new NumberDocumentTree();
        documentTreeWithLocalKnowledge = new NumberDocumentTree();
        stressReducedDocumentTree = new NumberDocumentTree();

        optimalDocumentTree = Utility.createDocuments( parameter, clusterMap );
        documentTreeWithGlobalKnowledge.level_order_insert( null, optimalDocumentTree, 0, parameter.getMaxCountOfCreatedDocuments() );
        documentTreeWithLocalKnowledge.level_order_insert( null, optimalDocumentTree, 0, parameter.getMaxCountOfCreatedDocuments() );
        stressReducedDocumentTree.level_order_insert( null, optimalDocumentTree, 0, parameter.getMaxCountOfCreatedDocuments() );
    }


    private void writeResults( Configuration parameter, SimulationResult[] simulationResults )
            throws IOException {
        String pathPrefix = System.getProperty( "user.home" ) + File.separator + "Results_DocumentTree" + File.separator;

        Utility.writeSimulationResults( pathPrefix + "globalKnowledge", parameter, simulationResults[SEARCH_MODE_GLOBAL]);
        Utility.writeSimulationResults( pathPrefix + "localKnowledge",  parameter, simulationResults[SEARCH_MODE_LOCAL]);
        Utility.writeSimulationResults( pathPrefix + "stressReduced",   parameter, simulationResults[SEARCH_MODE_STRESS_REDUCED]);
    }



    public static void main(String[] args)
            throws IOException {

        Simulation simulation = new Simulation();

        for( int i = 0; i < Setup.SETUP_LIST.size(); i++ ) {
            SimulationResult results[] = simulation.start( Setup.SETUP_LIST.get( i ));
            simulation.writeResults( Setup.SETUP_LIST.get( i ), results );
        }
    }

}
