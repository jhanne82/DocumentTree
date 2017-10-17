package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.simulation.Distribution;
import com.github.jhanne82.documenttree.simulation.SearchType;
import com.github.jhanne82.documenttree.simulation.SimulationApplication;
import org.junit.Test;


public class Simulation_Test {


    @Test
    public void doSimulation_1_1() {

        SimulationApplication simulationAppliction = new SimulationApplication( 100, 600,
                                                                              100, 3,
                                                                              50, 5,10);

        simulationAppliction.simulation( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false );
    }


    @Test
    public void doSimulation_2_1() {

        SimulationApplication simulationAppliction = new SimulationApplication( 100, 600,
                100, 3,
                50, 5, 10 );

        simulationAppliction.simulation( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false );
    }

}
