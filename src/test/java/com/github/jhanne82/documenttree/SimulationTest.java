package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.numbergenerator.Distribution;
import org.junit.Test;


public class SimulationTest {


    @Test
    public void simulation_1() {

        new Simulation( false,
                        Distribution.EQUALLY,
                        Distribution.EQUALLY );
    }


}
