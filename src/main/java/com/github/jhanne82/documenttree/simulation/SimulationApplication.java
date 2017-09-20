package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.simulation.numberdocument.NumberDocumentTreeSimulation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulationApplication {


    private final int MAX_COUNT_OF_CREATED_DOCUMENTS;
    private final int MAX_COUNT_OF_CREATED_SEARCHES;
    private final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR;
    private final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER;
    private final int LIMIT_FOR_LOCAL_KNOWLEDGE;
    private final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss" );


    public SimulationApplication() {

        MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
        MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
        MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
        MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
        LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
        NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20;
    }


    public SimulationApplication(int maxCountOfCreatedDocuments,
                                 int maxCountOfCreatedSearches,
                                 int maxCountOfTermsUsedToDefineAVector,
                                 int maxCountOfTermsWithQuantifier,
                                 int limitForLocalKnowledge,
                                 int numberOfSearchesBeforeRepositioning ) {

        this.MAX_COUNT_OF_CREATED_DOCUMENTS = maxCountOfCreatedDocuments;
        this.MAX_COUNT_OF_CREATED_SEARCHES = maxCountOfCreatedSearches;
        this.MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = maxCountOfTermsUsedToDefineAVector;
        this.MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = maxCountOfTermsWithQuantifier;
        this.LIMIT_FOR_LOCAL_KNOWLEDGE = limitForLocalKnowledge;
        this.NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = numberOfSearchesBeforeRepositioning;
    }

    /**
     * verwendetes Suchverfahren: Breitensuche
     * Clusterbildung: Nein
     * Verteilung der Dokumententerme: Gleichverteilt
     * Verteilung der Suchterme: Gleichverteilt
     */
    public void simulation_1_1() {
        System.out.println( "Simulation Nr: 1.1 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false );
    }



    public void simulation_1_2() {
        System.out.println( "Simulation Nr: 1.2 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false );
    }


    public void simulation_1_3() {
        System.out.println( "Simulation Nr: 1.3 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false );
    }



    public void simulation_1_4() {
        System.out.println( "Simulation Nr: 1.4 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false );
    }



    public void simulation_1_5() {
        System.out.println( "Simulation Nr: 1.5 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false );
    }



    public void simulation_1_6() {
        System.out.println( "Simulation Nr: 1.6 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false );
    }



    public void simulation_1_7() {
        System.out.println( "Simulation Nr: 1.7 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false );
    }



    public void simulation_1_8() {
        System.out.println( "Simulation Nr: 1.8 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false );
    }


    public void simulation_2_1() {
        System.out.println( "Simulation Nr: 2.1 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false );
    }



    public void simulation_2_2() {
        System.out.println( "Simulation Nr: 2.2 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false );
    }


    public void simulation_2_3() {
        System.out.println( "Simulation Nr: 2.3 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false );
    }



    public void simulation_2_4() {
        System.out.println( "Simulation Nr: 2.4 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false );
    }



    public void simulation_2_5() {
        System.out.println( "Simulation Nr: 2.5 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false );
    }



    public void simulation_2_6() {
        System.out.println( "Simulation Nr: 2.6 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false );
    }



    public void simulation_2_7() {
        System.out.println( "Simulation Nr: 2.7 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false );
    }



    public void simulation_2_8() {
        System.out.println( "Simulation Nr: 2.8 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false );
    }



    public void simulation_2_9() {
        System.out.println( "Simulation Nr: 2.9 START...... " + dateFormat.format( new Date() ) );
        simulation( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false );
    }



    private void simulation( SearchType   searchType,
                             Distribution distributionForDocument,
                             Distribution distributionForSearch,
                             boolean      cluster ) {

        SimulationSetup setup = new SimulationSetup( searchType,
                                                     distributionForDocument,
                                                     distributionForSearch,
                                                     MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                                                     MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                                                     MAX_COUNT_OF_CREATED_DOCUMENTS,
                                                     MAX_COUNT_OF_CREATED_SEARCHES,
                                                     LIMIT_FOR_LOCAL_KNOWLEDGE,
                                                     NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING,
                                                     cluster );

        DocumentTreeSimulation<Double> documentTreeSimulation = new NumberDocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( setup );

        SimulationResult result[] = documentTreeSimulation.startSearchSimulation( setup );
        printResult( result );
    }



    private void printResult( SimulationResult[] simulationResults ) {


        StringBuffer buffer = new StringBuffer();
        buffer.append( simulationResults[0].getSimulationSetup().toString() );
        buffer.append( '\n' );
        buffer.append( String.format( "%39s %20s%n", "global Knowledge", "local Knowledge" ) );
        buffer.append( String.format( "Hit/Miss Rate: %10d/%d %17d/%d%n",
                                      simulationResults[0].getHitRate(), simulationResults[0].getMissRate(),
                                      simulationResults[1].getHitRate(), simulationResults[1].getMissRate() ) );

        System.out.println( buffer.toString() );
    }

    

    public static void main( String[] args ) {

        SimulationApplication simulation = new SimulationApplication();
        simulation.simulation_1_1();
//        simulation.simulation_1_2();
//        simulation.simulation_1_3();
//        simulation.simulation_1_4();
//        simulation.simulation_1_5();
//        simulation.simulation_1_6();
//        simulation.simulation_1_7();
//        simulation.simulation_1_8();
//
        simulation.simulation_2_1();
//        simulation.simulation_2_2();
//        simulation.simulation_2_3();
//        simulation.simulation_2_4();
//        simulation.simulation_2_5();
//        simulation.simulation_2_6();
//        simulation.simulation_2_7();
//        simulation.simulation_2_8();

    }

}
