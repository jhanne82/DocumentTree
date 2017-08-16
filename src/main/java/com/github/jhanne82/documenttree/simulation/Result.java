package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.ResultDocumentList;

public class Result {


    // Bewertungskriterien
    private int averageSearchTime = 0;
    private int missRate = 0;
    private int hitRate = 0;
    private int averageCountOfRepositioning = 0;
    private int conformityOfOptimalDocumentTree = 0;
    private int conformityOfStressReducedDocumentTree = 0;
    private int distanceToOptimalPosition = 0;



    public void computeHitMissRate(ResultDocumentList resultDocumentList, Document bestMatch ) {

        Document doc = (Document)resultDocumentList.stream()
                                         .filter( x -> ((Document)x).getTermList().retainAll( bestMatch.getTermList() ) )
                                         .findFirst().orElse( null );

        if( doc == null ) {
            missRate++;
        } else {
            hitRate++;
        }
    }



    public String toString() {
        return "Hit/MissRate: " + hitRate + "/" + missRate;
    }


}
