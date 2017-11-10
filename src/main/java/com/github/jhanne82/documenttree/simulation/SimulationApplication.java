package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.simulation.number.NumberDocumentTreeSimulation;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import com.github.jhanne82.documenttree.simulation.utils.SimulationSetup;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Class to start the complete simulation of the searches on trees
 * with different search algorithm and access to the nodes.
 *
 * @author Jasmin Hannemann
 */
public class SimulationApplication {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss" );

    private static final int GLOBAL = 0;
    private static final int LOCAL  = 1;
    private static final int STRESS_REDUCED = 2;

    private final int MAX_COUNT_OF_CREATED_DOCUMENTS;
    private final int MAX_COUNT_OF_CREATED_SEARCHES;
    private final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR;
    private final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER;
    private final int LIMIT_FOR_LOCAL_KNOWLEDGE;
    private final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING;
    private final int THRESHOLD;



    // define the simulation like previously described in master thesis section "Simulation Setup"
    private final List<Parameter> parameterList = Arrays.asList(
             new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false  )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, true  )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, true )

            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false  )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, true  )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, true )

            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EQUALLY, false  )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EQUALLY, true  )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EXPONENTIALLY, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EQUALLY, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EQUALLY, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, true )
            ,new Parameter( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, true )
    );



    /**
     * Private standard constructor which defines the default parameter
     * like defined in my master thesis section "Simulation Setup".
     */
    private SimulationApplication() {

        this( 1000, 1000000,
              1000, 3,
              300, 20, 100 );
    }



    /**
     * Constructor with params to define the setup of the simulation which should be started.
     *
     * @param maxCountOfCreatedDocuments - defines the number of documents on which should be searched
     * @param maxCountOfCreatedSearches  - defines the number searches which will be executed
     * @param maxCountOfTermsUsedToDefineAVector - defines how many terms are used to describe a document/search
     * @param maxCountOfTermsWithQuantifier - defines how many terms should be not null to desribe a document/search
     * @param limitForLocalKnowledge - defines the limit on how many documents should be searched until search will be aborted for local knowledge
     * @param numberOfSearchesBeforeRepositioning - defines the number of searches on the document before it can change the position in the tree
     * @param threshold - defines the threshold on searches which were not performed on a document until the document can be moved up in the tree
     */
    public SimulationApplication(int maxCountOfCreatedDocuments,
                                 int maxCountOfCreatedSearches,
                                 int maxCountOfTermsUsedToDefineAVector,
                                 int maxCountOfTermsWithQuantifier,
                                 int limitForLocalKnowledge,
                                 int numberOfSearchesBeforeRepositioning,
                                 int threshold ) {

        this.MAX_COUNT_OF_CREATED_DOCUMENTS = maxCountOfCreatedDocuments;
        this.MAX_COUNT_OF_CREATED_SEARCHES = maxCountOfCreatedSearches;
        this.MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = maxCountOfTermsUsedToDefineAVector;
        this.MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = maxCountOfTermsWithQuantifier;
        this.LIMIT_FOR_LOCAL_KNOWLEDGE = limitForLocalKnowledge;
        this.NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = numberOfSearchesBeforeRepositioning;
        this.THRESHOLD = threshold;
    }



    /**
     * Method will start the simulation with the given simulation parameter.
     *
     * @param searchType - specifies the used search which will be performed on the trees. See SearchType.
     * @param distributionForDocument - specifies the used distribution when creating the termins which defines the documents
     * @param distributionForSearch - specifies the used distribution when creating the termins which defines the search
     * @param cluster - boolean parameter to define if cluster will be used to simulate the impact of the usage of synonyms
     * @param printInFile - boolean parameter to define if a short result will print on screen or the complete result of the simulation will write into files of the home directory
     */
    public void simulation( SearchType searchType,
                            Distribution distributionForDocument,
                            Distribution distributionForSearch,
                            boolean cluster,
                            boolean printInFile ) {

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
                                                     THRESHOLD,
                                                     cluster );

        DocumentTreeSimulation<Double> documentTreeSimulation = new NumberDocumentTreeSimulation();
        documentTreeSimulation.setupRequiredDocumentTrees( setup );

        SimulationResult result[] = documentTreeSimulation.startSearchSimulation( setup );
        printResult( result, printInFile );

    }



    private void startSimulationWithThreads() {

//        ExecutorService service = Executors.newFixedThreadPool( 3 );
//
//        parameterList.forEach( i -> service.submit( () -> {
//
//            SimulationApplication simulation = new SimulationApplication();
//            simulation.simulation( i.searchType, i.distributionForDocument, i.distributionForSearch, i.cluster, true );
//        } ) );
//        service.shutdown();
//        

        /*parameterList.forEach( i -> {
            SimulationApplication simulation = new SimulationApplication();
            simulation.simulation( i.searchType, i.distributionForDocument, i.distributionForSearch, i.cluster, false );
        } );      */

        for( int i = 0; i< parameterList.size(); i = i+2 ) {
            int finalI = i;
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Parameter parameter = parameterList.get(finalI);
                    SimulationApplication simulation = new SimulationApplication();
                    simulation.simulation( parameter.searchType, parameter.distributionForDocument,
                            parameter.distributionForSearch, parameter.cluster, true );
                }
            });
            thread1.start();
            if( (i+1)< parameterList.size() ) {
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Parameter parameter = parameterList.get(finalI+1);
                        SimulationApplication simulation = new SimulationApplication();
                        simulation.simulation( parameter.searchType, parameter.distributionForDocument,
                                parameter.distributionForSearch, parameter.cluster, true );
                    }
                });
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



    private void printResult( SimulationResult[] simulationResults, boolean printInFile ) {

        if( printInFile ) {
            try {
                writeResultsIntoFiles( simulationResults );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        } else {
            String str = simulationResults[ 0 ].getSimulationSetup().toString() +
                         '\n' +
                         String.format( "%39s %20s%n", "global Knowledge", "local Knowledge" ) +
                         String.format( "Hit/Miss Rate: %10d/%d %17d/%d%n",
                                        simulationResults[ 0 ].getHitRate(), simulationResults[ 0 ].getMissRate(),
                                        simulationResults[ 1 ].getHitRate(), simulationResults[ 1 ].getMissRate() ) +
                         String.format( "NodesOnCorrectLevel: %10d %17d%n",
                                        simulationResults[ 0 ].getDocumentOnCorrectLevel(),
                                        simulationResults[ 1 ].getDocumentOnCorrectLevel() );

            System.out.println( str );
        }
    }



    private void writeResultsIntoFiles( SimulationResult[] results )
            throws IOException {

        String userHome = System.getProperty( "user.home" );
        String globalPath = userHome + File.separator  + "Results_DocumentTree" + File.separator  + "globalKnowledge";
        String localPath  = userHome + File.separator  + "Results_DocumentTree" + File.separator  + "localKnowledge";
        String stressPath = userHome + File.separator  + "Results_DocumentTree" + File.separator  + "stressReduced";

        FileUtils.forceMkdir( new File( globalPath ) );
        FileUtils.forceMkdir( new File( localPath ) );
        FileUtils.forceMkdir( new File( stressPath ) );

        writeResultIntoFiles( globalPath, results[GLOBAL] );
        writeResultIntoFiles( localPath, results[LOCAL] );
        writeResultIntoFiles( stressPath, results[STRESS_REDUCED] );
    }



    private void writeResultIntoFiles( String path, SimulationResult result )
            throws IOException {

        SimulationSetup setup = result.getSimulationSetup();

        if( setup.cluster ) {
            path += File.separator + "clustered";
        } else {
            path += File.separator + "not_clustered";
        }

        path = path + File.separator + setup.searchType
                    + File.separator + setup.distributionForDocumentVector
                    + File.separator + setup.distributionForSearchVector;

        FileUtils.forceMkdir( new File( path ) );

        writeInFile( path + File.separator + "HitMissRate.txt",
                     result.getHitRate() + "/" + result.getMissRate() );
        writeInFile( path + File.separator + "RequiredSearches.txt",
                     Arrays.toString( result.getRequiredSearches().toArray() ) );
        writeInFile( path + File.separator + "VisitedNodes.txt",
                     Arrays.toString( result.getRequiredRepositionings().toArray() ) );
        writeInFile( path + File.separator + "RequiredRepositionings.txt",
                     Arrays.toString( result.getRequiredRepositionings().toArray() ) );
        writeInFile( path + File.separator + "DocumentsOnCorrectLevelInTree.txt",
                     String.valueOf( result.getDocumentOnCorrectLevel() ) );
        writeInFile( path + File.separator + "DistanceToCorrectLevel.txt",
                     Arrays.toString( result.getDistanceToOptimalPosition().toArray() ));

    }



    private void writeInFile( String path, String content )
            throws IOException {
        File file = new File( path );
        PrintWriter writer = new PrintWriter( new BufferedWriter( new FileWriter( path, true ) ) );
        writer.println( content );
        writer.close();
    }



    public static void main( String[] args ) {

        for( int i = 0; i < 20; i++) {
            System.out.println("Durchlauf.............." + (i+1));
            SimulationApplication simulation = new SimulationApplication();
            simulation.startSimulationWithThreads();
        }
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
