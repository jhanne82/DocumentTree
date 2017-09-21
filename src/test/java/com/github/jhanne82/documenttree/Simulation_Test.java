package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.simulation.SimulationApplication;
import org.junit.Test;


public class Simulation_Test {


    @Test
    public void doSimulation_1_1() {

        SimulationApplication simulationAppliction = new SimulationApplication( 100, 600,
                                                                              100, 3,
                                                                              50, 5);

        simulationAppliction.simulation_1_1();
    }


    @Test
    public void doSimulation_2_1() {

        SimulationApplication simulationAppliction = new SimulationApplication( 100, 600,
                100, 3,
                50, 5);

        simulationAppliction.simulation_2_1();
    }

}
