package com.github.jhanne82.documenttree;



import com.github.jhanne82.documenttree.simulation.Simulation;
import com.github.jhanne82.documenttree.simulation.configuration.Configuration;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType;
import com.github.jhanne82.documenttree.simulation.utils.SimulationResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;



@RunWith( Parameterized.class )
public class Simulation_Test {



    @Parameterized.Parameters
    public static Configuration[] data() {
        return new Configuration[] { new TestConfiguration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false ),
                                     new TestConfiguration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, true ),
                                     new TestConfiguration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EQUALLY, true ),
                                     new TestConfiguration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false ),
                                     new TestConfiguration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false ),
                                     new TestConfiguration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
        };
    }



    @Parameterized.Parameter 
    public /* NOT private */ Configuration parameter;

    @Test
    public void testSimulation() {
        Simulation simulation = new Simulation();
        SimulationResult[] results = simulation.start( parameter );

        printResult( results );
    }



    private void printResult( SimulationResult[] results ) {
        String str = results[ 0 ].getParameter().toString() +
                     '\n' +
                     String.format( "%39s %20s%n", "global Knowledge", "local Knowledge" ) +
                     String.format( "Hit/Miss Rate: %10d %17d%n",
                                    results[ 0 ].getHits(),
                                    results[ 1 ].getHits() ) +
                     String.format( "NodesOnCorrectLevel: %10d %17d%n",
                                    results[ 0 ].getDocumentOnCorrectLevel(),
                                    results[ 1 ].getDocumentOnCorrectLevel() );

        System.out.println( str );
    }



    private static class TestConfiguration
        extends Configuration {


        TestConfiguration( SearchType   searchType,
                           Distribution distributionForDocument,
                           Distribution distributionForSearch,
                           boolean      cluster ) {

            super(searchType, distributionForDocument, distributionForSearch, cluster );
        }


        @Override
        public int getMaxCountOfCreatedDocuments() {
            return 100;
        }


        @Override
        public int getMaxCountOfCreatedSearches() {
            return 600;
        }


        @Override
        public int getMaxCountOfTermsUsedToDefineVector() {
            return 100;
        }


        @Override
        public int getMaxCountOfTermsWithQuantifier() {
            return 3;
        }



        @Override
        public int getLimitForLocalKnowledge() {
            return 50;
        }


        @Override
        public int getNumberOfSearchesBeforeRepositioning() {
            return 5;
        }


        @Override
        public int getThreshold() {
            return 10;
        }


    }

}
