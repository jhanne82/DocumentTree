package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.DocumentTree;
import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.configuration.Configuration;
import com.github.jhanne82.documenttree.simulation.configuration.Parameter;
import com.github.jhanne82.documenttree.simulation.documenttree.NumberDocumentTree;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import com.github.jhanne82.documenttree.simulation.utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Simulation {



    private static final int SEARCH_MODE_GLOBAL = 0;
    private static final int SEARCH_MODE_LOCAL  = 1;
    private static final int SEARCH_MODE_STRESS_REDUCED = 2;


    private DocumentTree<Double> documentTreeWithGlobalKnowledge;
    private DocumentTree<Double> documentTreeWithLocalKnowledge;
    private DocumentTree<Double> stressReducedDocumentTree;
    private Document<Double>[]   optimalDocumentTree;



    public SimulationResult[] start( Parameter parameter ) {
        Map<Double, Double> clusterMap = null;
        if( parameter.isCluster() ) {
            clusterMap = Utility.createClusterMap();
        }

        setupDocumentTress( parameter, clusterMap );

        DocumentTreeSimulation documentTreeSimulation = new DocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( parameter );

        return documentTreeSimulation.startSearchSimulation( parameter );
    }



    private void setupDocumentTress( Parameter parameter, Map<Double, Double> clusterMap ) {
        documentTreeWithGlobalKnowledge = new NumberDocumentTree();
        documentTreeWithLocalKnowledge = new NumberDocumentTree();
        stressReducedDocumentTree = new NumberDocumentTree();

        optimalDocumentTree = Utility.createDocuments( parameter, clusterMap );
        documentTreeWithGlobalKnowledge.level_order_insert( null, optimalDocumentTree, 0, parameter.getMaxCountOfCreatedDocuments() );
        documentTreeWithLocalKnowledge.level_order_insert( null, optimalDocumentTree, 0, parameter.getMaxCountOfCreatedDocuments() );
        stressReducedDocumentTree.level_order_insert( null, optimalDocumentTree, 0, parameter.getMaxCountOfCreatedDocuments() );
    }


    private void writeResults( Parameter parameter, SimulationResult[] simulationResults )
            throws IOException {
        String pathPrefix = System.getProperty( "user.home" ) + File.separator + "Results_DocumentTree_NEW" + File.separator;

        Utility.writeSimulationResults( pathPrefix + "globalKnowledge", parameter, simulationResults[SEARCH_MODE_GLOBAL]);
        Utility.writeSimulationResults( pathPrefix + "localKnowledge",  parameter, simulationResults[SEARCH_MODE_LOCAL]);
        Utility.writeSimulationResults( pathPrefix + "stressReduced",   parameter, simulationResults[SEARCH_MODE_STRESS_REDUCED]);
    }



    public static void main(String[] args)
            throws IOException {

        Simulation simulation = new Simulation();

        for( int i = 0; i < Configuration.getNumberOfConfigurations(); i++ ) {
            SimulationResult results[] = simulation.start( Configuration.getConfigurationParameter( i ));
            simulation.writeResults( Configuration.getConfigurationParameter( i ), results );
        }
    }

}
