package com.github.jhanne82.documenttree.simulation.configuration;



import com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution;
import com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType;

import java.util.Arrays;
import java.util.List;



/**
 * Interface which defines the setup of the simulation.
 * The interface has a list with all parameters which can be changes in each simulation like:
 * <li>{@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.SearchType}</li>
 * <li>{@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution} for the document terms</li>
 * <li>{@link com.github.jhanne82.documenttree.simulation.configuration.enumeration.Distribution} for the search terms</li>
 * <li>if cluster for terms should be used</li>
 */
public interface Setup {



    /**
     * Defines a list with all possible configurations.
     * The configurations are described in master thesis section "Simulation Setup"
     */
    List<Configuration> SETUP_LIST = Arrays.asList(
             new Configuration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false  )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, true  )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.DEPTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, true )

            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, false  )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EQUALLY, true  )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EQUALLY, Distribution.EXPONENTIALLY, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EQUALLY, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EQUALLY, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.BREADTH_FIRST, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, true )

            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EQUALLY, false  )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EQUALLY, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EQUALLY, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, false )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EQUALLY, true  )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EQUALLY, Distribution.EXPONENTIALLY, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EQUALLY, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.GAUSSIAN, Distribution.EXPONENTIALLY, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EQUALLY, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.GAUSSIAN, true )
            ,new Configuration( SearchType.RANDOM_WALKER, Distribution.EXPONENTIALLY, Distribution.EXPONENTIALLY, true )
    );

}
