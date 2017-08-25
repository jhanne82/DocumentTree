package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.simulation.SimulationAppliction;
import org.junit.Test;


public class Simulation_Test {


/*    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
    private static final int LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
    private static final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20; */

    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 100;
    private static final int MAX_COUNT_OF_CREATED_SEARCHES = 10000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 100;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
    private static final int LIMIT_FOR_LOCAL_KNOWLEDGE = 30;
    private static final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 5;





    @Test
    public void doSimulation_1_1() {

        SimulationAppliction simulationAppliction = new SimulationAppliction();
        simulationAppliction.simulation_1_1();
    }

}
