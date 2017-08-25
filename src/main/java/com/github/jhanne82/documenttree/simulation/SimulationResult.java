package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.ResultDocumentList;

public class SimulationResult {



    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_CREATED_SEARCHES = 1000000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_VECTOR = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
    private static final int LIMIT_FOR_LOCAL_KNOWLEDGE = 300;
    private static final int NUMBER_OF_SEARCHES_BEFORE_REPOSITIONING = 20;


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
