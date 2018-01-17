package com.github.jhanne82.documenttree.simulation.enumeration;


/**
 *  Distributions that can be used to define the distribution of the document and search terms.
 *  <li>{@link #EQUALLY}</li>
 *  <li>{@link #GAUSSIAN}</li>
 *  <li>{@link #EXPONENTIALLY}</li>
 */
public enum Distribution {

    /**
     *  Each possible value will occur with the same probability.
     */
    EQUALLY,

    /**
     * The probability of the values are described by the Gaussian function.
     */
    GAUSSIAN,

    /**
     * The probability of the possible values a given by the exponential function.
     */
    EXPONENTIALLY
}
