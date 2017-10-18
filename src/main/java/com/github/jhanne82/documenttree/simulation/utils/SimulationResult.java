package com.github.jhanne82.documenttree.simulation.utils;

import java.util.ArrayList;
import java.util.List;

public class SimulationResult {


    private final SimulationSetup simulationSetup;

    // Bewertungskriterien
    private List<Integer> requiresSearches;
    private int missRate = 0;
    private int hitRate = 0;
    private List<Integer> averageCountOfRepositioning;
    private int conformityOfOptimalDocumentTree = 0;
    private int conformityOfStressReducedDocumentTree = 0;
    private int distanceToOptimalPosition = 0;



    public SimulationResult( SimulationSetup simulationSetup ) {
        this.simulationSetup = simulationSetup;
    }


    public int getHitRate() {return hitRate; }
    public void setHitRate( int hitRate ) { this.hitRate = hitRate; }


    public int getMissRate() { return missRate; }
    public void setMissRate( int missRate ) { this.missRate = missRate; }



    public List<Integer> getRequiredSearches() { return requiresSearches; }
    public void addRequiredSearches( int requiredSearches ) {
        if( null == requiresSearches ) {
            requiresSearches = new ArrayList<>();
        }
        requiresSearches.add( requiredSearches );
    }


    public List<Integer> getRequiredRepositionings() { return averageCountOfRepositioning; }
    public void addRequiredRepositioning( int requiredRepositioning ) {
        if( null == averageCountOfRepositioning ) {
            averageCountOfRepositioning = new ArrayList<>();
        }
        averageCountOfRepositioning.add( requiredRepositioning );
    }


    public SimulationSetup getSimulationSetup() {
        return simulationSetup;
    }


}
