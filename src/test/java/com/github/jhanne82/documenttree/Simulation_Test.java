package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.document.Term;
import com.github.jhanne82.documenttree.simulation.Distribution;
import com.github.jhanne82.documenttree.simulation.DocumentTreeSimulation;
import com.github.jhanne82.documenttree.simulation.SearchType;
import com.github.jhanne82.documenttree.simulation.numberdocument.NumberSimulationUtils;
import org.junit.Test;

import java.util.List;


public class Simulation_Test {


    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
    private static final int LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
    private static final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20;






    @Test
    public void doSimulation_1_1() {

        DocumentTreeSimulation simulation = NumberSimulationUtils.setupSimulation( Distribution.EQUALLY,
                                                                                   MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                                                                                   MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                                                                                   MAX_COUNT_OF_CREATED_DOCUMENTS,
                                                                                   false );

        System.out.println( "CreateSearchTerms START...");
        List<List<Term<Double>>> searchTermVectorList = NumberSimulationUtils.createSearchTermVectorList( Distribution.EQUALLY,
                                                                                                MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR,
                                                                                                MAX_COUNT_OF_TERMS_WITH_QUANTIFIER,
                                                                                                MAX_COUNT_OF_CREATED_SEARCHES );
        System.out.println( "CreateSearchTerms END...");

        simulation.runSimulation( searchTermVectorList,
                                  SearchType.DEPTH_FIRST,
                                  LIMIT_FOR_LOCAL_KNOWLEDGE,
                                  NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING );

    }

}
