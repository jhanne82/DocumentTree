package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.simulation.configuration.Configuration;
import com.github.jhanne82.documenttree.simulation.configuration.Parameter;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import com.github.jhanne82.documenttree.simulation.utils.Utility;

import java.io.File;
import java.io.IOException;

public class Simulation {



    private static final int SEARCH_MODE_GLOBAL = 0;
    private static final int SEARCH_MODE_LOCAL  = 1;
    private static final int SEARCH_MODE_STRESS_REDUCED = 2;



    public SimulationResult[] start( Parameter parameter ) {
        DocumentTreeSimulation documentTreeSimulation = new DocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( parameter );

        return documentTreeSimulation.startSearchSimulation( parameter );
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
