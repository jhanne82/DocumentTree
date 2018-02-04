package com.github.jhanne82.documenttree.simulation.utils;

import com.github.jhanne82.documenttree.simulation.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation to store the results of a simulation.
 * The results which are stored are defined in the master thesis.
 */
public class SimulationResult {



    private final Configuration parameter;

    private List<Integer> requiredSearches;
    private int hits = 0;
    private List<Integer> repositionings;
    private int documentOnCorrectLevel = 0;
    private List<Integer> distanceToOptimalPosition;



    /**
     * Constructs a SimulationResult object by a given Configuration
     *
     * @param parameter defines the configuration which was used for the simulation.
     */
    public SimulationResult( Configuration parameter ) {
        this.parameter = parameter;
    }



    /**
     * Get the number if Hits after all searches.
     *
     * @return the number of hits
     */
    public int getHits() {return hits; }



    /**
     * Set the current number of hits after  all searches are performed.
     *
     * @param hits defines the number of hits.
     */
    public void setHits(int hits) { this.hits = hits; }



    /**
     * Get the number of documents which are on the same level like the copies of them in the optimal tree.
     *
     * @return the number of documents on the correct level.
     */
    public int getDocumentOnCorrectLevel() { return documentOnCorrectLevel; }



    /**
     * Sets the number of documents which are on the same level like the copies of them in the optimal tree.
     *
     * @param documentOnCorrectLevel defines the number of documents on the correct level.
     */
    public void setDocumentOnCorrectLevel( int documentOnCorrectLevel ) { this.documentOnCorrectLevel = documentOnCorrectLevel; }



    /**
     * Get the list of number of required searches until the document with the highest relevance was found.
     *
     * @return the number of searches.
     */
    public List<Integer> getRequiredSearches() { return requiredSearches; }



    /**
     * Add the number of required searches to find the document with the highest relevance for the last search.
     *
     * @param requiredSearches defines the number of required searches.
     */
    public void addRequiredSearches( int requiredSearches ) {
        if( null == this.requiredSearches) {
            this.requiredSearches = new ArrayList<>();
        }
        this.requiredSearches.add( requiredSearches );
    }



    /**
     * Get a list of counts of performed repositionings.
     *
     * @return a list of repositionings
     */
    public List<Integer> getRequiredRepositionings() { return repositionings; }



    /**
     * Add the number of performed repositionings.
     *
     * @param requiredRepositioning defines the last number of repositionings.
     */
    public void addRequiredRepositioning( int requiredRepositioning ) {
        if( null == repositionings) {
            repositionings = new ArrayList<>();
        }
        repositionings.add( requiredRepositioning );
    }



    /**
     * Get a list which contains the distance of each document from the optimal position.
     *
     * @return the list of distances to optimum position
     */
    public List<Integer> getDistanceToOptimalPosition() { return distanceToOptimalPosition; }



    /**
     * Add a distance to the optimum position for a special document.
     *
     * @param distance defines the distance for a special document.
     */
    public void addDistanceToOptimalPosition( int distance ) {
        if( null == distanceToOptimalPosition ) {
            distanceToOptimalPosition = new ArrayList<>();
        }
        distanceToOptimalPosition.add( distance );
    }



    /**
     * Get the defined parameter which were used for the simulation.
     *
     * @return the parameter for the simulation.
     */
    public Configuration getParameter() {
        return parameter;
    }


}
