package com.github.jhanne82.documenttree.simulation.configuration;

import com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType;

import java.util.Arrays;
import java.util.List;

public class Configuration {



    // define the simulation like previously described in master thesis section "Simulation Setup"
    private static final List<Parameter> parameterList = Arrays.asList(
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

    

    public static Parameter getConfigurationParameter( int i) {
        return parameterList.get( i );
    }



    public static int getNumberOfConfigurations() {
        return parameterList.size();
    }

}
