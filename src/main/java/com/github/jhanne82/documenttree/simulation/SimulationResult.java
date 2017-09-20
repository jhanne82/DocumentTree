package com.github.jhanne82.documenttree.simulation;

public class SimulationResult {


    private final SimulationSetup simulationSetup;

    // Bewertungskriterien
    private int averageSearchTime = 0;
    private int missRate = 0;
    private int hitRate = 0;
    private int averageCountOfRepositioning = 0;
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



    public SimulationSetup getSimulationSetup() {
        return simulationSetup;
    }


}
