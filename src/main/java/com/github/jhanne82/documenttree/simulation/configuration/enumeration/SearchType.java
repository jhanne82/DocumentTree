package com.github.jhanne82.documenttree.simulation.configuration.enumeration;



/**
 *  Search types that can be used to define how the tree should be searched.
 *  <li> {@link #BREADTH_FIRST}
 *  <li> {@link #DEPTH_FIRST}
 *  <li> {@link #RANDOM_WALKER}
 */
public enum SearchType {

    /**
     *  It starts at the root and explores the neighbor nodes first, before moving to the next level neighbours.
     */
    BREADTH_FIRST,

    /**
     * One starts at the root and explores as far as possible along each branch before backtracking.
     */
    DEPTH_FIRST,

    /**
     * The tree will be searched randomly. After each searched node it will be randomly determined if the next node
     * will be a neighbor node or a node from the branch.
     */
    RANDOM_WALKER
}
