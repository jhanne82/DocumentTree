package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.simulation.numberdocument.NumberDocumentTreeSimulation;

public class SimulationAppliction {


    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
    private static final int LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
    private static final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20;

    /**
     * verwendetes Suchverfahren: Breitensuche
     * Clusterbildung: Nein
     * Verteilung der Dokumententerme: Gleichverteilt
     * Verteilung der Suchterme: Gleichverteilt
     */
    public void simulation_1_1() {

        System.out.println("Simulation Nr: 1.1: Start");
        DocumentTreeSimulation<Double> documentTreeSimulation = new NumberDocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( Distribution.EQUALLY,
                                                           MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                                                           MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                                                           MAX_COUNT_OF_CREATED_DOCUMENTS,
                                                           false );

        SimulationResult result = documentTreeSimulation.startSearchSimulation( Distribution.EQUALLY,
                SearchType.BREADTH_FIRST,
                MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                MAX_COUNT_OF_CREATED_SEARCHES,
                LIMIT_FOR_LOCAL_KNOWLEDGE,
                NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING );

        System.out.println( result);

        System.out.println("Simulation Nr: 1.1: Ende");

    }





    public static void main( String[] args ) {

        SimulationAppliction simulation = new SimulationAppliction();
        simulation.simulation_1_1();

    }

}
