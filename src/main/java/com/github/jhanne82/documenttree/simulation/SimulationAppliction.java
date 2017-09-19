package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.simulation.numberdocument.NumberDocumentTreeSimulation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulationAppliction {


    private final int MAX_COUNT_OF_CREATED_DOCUMENTS;
    private final int MAX_COUNT_OF_CREATED_SEARCHES;
    private final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR;
    private final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER;
    private final int LIMIT_FOR_LOCAL_KNOWLEDGE;
    private final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING;


    public SimulationAppliction() {

        MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
        MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
        MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
        MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
        LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
        NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20;
    }


    public SimulationAppliction( int maxCountOfCreatedDocuments,
                                 int maxCountOfCreatedSearches,
                                 int maxCountOfTermsUsedToDefineAVector,
                                 int maxCountOfTermsWithQuantifier,
                                 int limitForLocalKnowledge,
                                 int numberOfSerchesBeforeRepositioning ) {

        this.MAX_COUNT_OF_CREATED_DOCUMENTS = maxCountOfCreatedDocuments;
        this.MAX_COUNT_OF_CREATED_SEARCHES = maxCountOfCreatedSearches;
        this.MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = maxCountOfTermsUsedToDefineAVector;
        this.MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = maxCountOfTermsWithQuantifier;
        this.LIMIT_FOR_LOCAL_KNOWLEDGE = limitForLocalKnowledge;
        this.NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = numberOfSerchesBeforeRepositioning;
    }

    /**
     * verwendetes Suchverfahren: Breitensuche
     * Clusterbildung: Nein
     * Verteilung der Dokumententerme: Gleichverteilt
     * Verteilung der Suchterme: Gleichverteilt
     */
    public void simulation_1_1() {

        System.out.println( "Simulation Nr: 1.1 START...... " + (new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss" ).format( new Date() )));

        SimulationSetup setup = new SimulationSetup( SearchType.BREADTH_FIRST,
                                                     Distribution.EQUALLY,
                                                     Distribution.EQUALLY,
                                                     MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                                                     MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                                                     MAX_COUNT_OF_CREATED_DOCUMENTS,
                                                     MAX_COUNT_OF_CREATED_SEARCHES,
                                                     LIMIT_FOR_LOCAL_KNOWLEDGE,
                                                     NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING,
                                                     false );

        DocumentTreeSimulation<Double> documentTreeSimulation = new NumberDocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( setup );

        SimulationResult result[] = documentTreeSimulation.startSearchSimulation( setup );

        System.out.println( "Ergebnis für Global Knowledge:");
        System.out.println( result[0]);

        System.out.println( "\n\n\n");

        System.out.println( "Ergebnis für local Knowledge:");
        System.out.println( result[1]);

        System.out.println( "Simulation Nr: 1.1 ENDE...... " + (new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss" ).format( new Date() )));

    }

    

    public static void main( String[] args ) {

        SimulationAppliction simulation = new SimulationAppliction();
        simulation.simulation_1_1();

    }

}
