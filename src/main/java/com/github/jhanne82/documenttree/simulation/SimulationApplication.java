package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.simulation.numberdocument.NumberDocumentTreeSimulation;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SimulationApplication {


    private final int MAX_COUNT_OF_CREATED_DOCUMENTS;
    private final int MAX_COUNT_OF_CREATED_SEARCHES;
    private final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR;
    private final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER;
    private final int LIMIT_FOR_LOCAL_KNOWLEDGE;
    private final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING;
    private final int TRESHOLD;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss" );


    public SimulationApplication() {

        MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
        MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
        MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
        MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
        LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
        NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20;
        TRESHOLD = 100;
    }


    public SimulationApplication(int maxCountOfCreatedDocuments,
                                 int maxCountOfCreatedSearches,
                                 int maxCountOfTermsUsedToDefineAVector,
                                 int maxCountOfTermsWithQuantifier,
                                 int limitForLocalKnowledge,
                                 int numberOfSearchesBeforeRepositioning,
                                 int treshold ) {

        this.MAX_COUNT_OF_CREATED_DOCUMENTS = maxCountOfCreatedDocuments;
        this.MAX_COUNT_OF_CREATED_SEARCHES = maxCountOfCreatedSearches;
        this.MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = maxCountOfTermsUsedToDefineAVector;
        this.MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = maxCountOfTermsWithQuantifier;
        this.LIMIT_FOR_LOCAL_KNOWLEDGE = limitForLocalKnowledge;
        this.NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = numberOfSearchesBeforeRepositioning;
        this.TRESHOLD = treshold;
    }



    public void simulation( SearchType searchType,
                            Distribution distributionForDocument,
                            Distribution distributionForSearch,
                            boolean cluster ) {

        System.out.println( "Simulation START...... " + dateFormat.format( new Date() ) );

        SimulationSetup setup = new SimulationSetup( searchType,
                                                     distributionForDocument,
                                                     distributionForSearch,
                                                     MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                                                     MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                                                     MAX_COUNT_OF_CREATED_DOCUMENTS,
                                                     MAX_COUNT_OF_CREATED_SEARCHES,
                                                     LIMIT_FOR_LOCAL_KNOWLEDGE,
                                                     NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING,
                                                     TRESHOLD,
                                                     cluster );

        DocumentTreeSimulation<Double> documentTreeSimulation = new NumberDocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( setup );

        SimulationResult result[] = documentTreeSimulation.startSearchSimulation( setup );
        printResult( result, false );

        System.out.println( "Simulation END " + dateFormat.format( new Date() ) );

    }



    private void printResult( SimulationResult[] simulationResults, boolean printInFile ) {

        StringBuffer buffer = new StringBuffer();
        buffer.append( simulationResults[0].getSimulationSetup().toString() );
        buffer.append( '\n' );
        buffer.append( String.format( "%39s %20s%n", "global Knowledge", "local Knowledge" ) );
        buffer.append( String.format( "Hit/Miss Rate: %10d/%d %17d/%d%n",
                                      simulationResults[0].getHitRate(), simulationResults[0].getMissRate(),
                                      simulationResults[1].getHitRate(), simulationResults[1].getMissRate() ) );
        buffer.append('\n');
        buffer.append("Required Searches to optimal Document \n");
        buffer.append("global: " +  Arrays.toString( simulationResults[0].getRequiredSearches().toArray()));
        buffer.append("local: " +  Arrays.toString( simulationResults[1].getRequiredSearches().toArray()));


        System.out.println( buffer.toString() );
    }


    List<Parameter> parameterList = Arrays.asList( new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false  )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )

                                                  ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false  )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )

//                                                  ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EQUALLY, false  )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
//                                                   ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
    );


    private void startSimulationWithThreads() {

        /*ExecutorService service = Executors.newFixedThreadPool( 10);

        parameterList.forEach( i -> service.submit( () -> {

            SimulationApplication simulation = new SimulationApplication();
            simulation.simulation( i.searchType, i.distributionForDocument, i.distributionForSearch, i.cluster );
        } ) );    */

        parameterList.forEach( i ->  {

            SimulationApplication simulation = new SimulationApplication();
            simulation.simulation( i.searchType, i.distributionForDocument, i.distributionForSearch, i.cluster );
        }  );
    }

    

    public static void main( String[] args ) {

        SimulationApplication simulation = new SimulationApplication();
        simulation.startSimulationWithThreads();
    }



    private class Parameter {

        SearchType   searchType;
        Distribution distributionForDocument;
        Distribution distributionForSearch;
        boolean      cluster;

        Parameter( SearchType   searchType,
                   Distribution distributionForDocument,
                   Distribution distributionForSearch,
                   boolean      cluster  ) {

            this.searchType = searchType;
            this.distributionForDocument = distributionForDocument;
            this.distributionForSearch = distributionForSearch;
            this.cluster = cluster;
        }
    }

}
