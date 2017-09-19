package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.simulation.SimulationAppliction;
import org.junit.Test;


public class Simulation_Test {


    @Test
    public void doSimulation_1_1() {

        SimulationAppliction simulationAppliction = new SimulationAppliction( 100, 600,
                                                                              100, 3,
                                                                              50, 5);

        simulationAppliction.simulation_1_1();
    }

}
