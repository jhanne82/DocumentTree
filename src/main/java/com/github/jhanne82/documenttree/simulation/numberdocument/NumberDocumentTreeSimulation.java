package com.github.jhanne82.documenttree.simulation.numberdocument;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.numberdocument.NumberDocumentTree;
import com.github.jhanne82.documenttree.simulation.Distribution;
import com.github.jhanne82.documenttree.simulation.DocumentTreeSimulation;
import com.github.jhanne82.documenttree.simulation.SimulationSetup;
import com.github.jhanne82.documenttree.utils.EulerianDistance;
import com.github.jhanne82.documenttree.utils.RandomNumberGenerator;


public class NumberDocumentTreeSimulation
    extends DocumentTreeSimulation <Double> {

    private RandomNumberGenerator randomNumberGenerator;

    public NumberDocumentTreeSimulation() {
        super();
        randomNumberGenerator = new RandomNumberGenerator();
    }


    @Override
    protected Document<Double> searchOnOptimalDocumentTree( Double[] searchTermVector ) {

        Document<Double> bestMatch = null;
        double relevanceOfBestMatch = 0;

        for ( Document<Double> document : optimalDocumentTree ) {

            double euleriaDistance = EulerianDistance.calEulerianDistance( document.getTermList(), searchTermVector );
            double relevance = EulerianDistance.transformEulerianDistanceToRelevanceValue( euleriaDistance );
            document.addRelevance( relevance );

            if( bestMatch == null || relevance > relevanceOfBestMatch ) {
                bestMatch = document;
                relevanceOfBestMatch = relevance;
            }
        }

        return bestMatch;
    }

    @Override
    protected void setupRequiredDocumentTrees(SimulationSetup setup ) {

        documentTreeWithGlobalKnowledge = new NumberDocumentTree();
        documentTreeWithLocalKnowledge = new NumberDocumentTree();
        stressReducedDocumentTree = new NumberDocumentTree();

        optimalDocumentTree = createDocuments( setup.distributionForDocumentVector,
                                               setup.countOfTermsUsedToDefineVector,
                                               setup.countOfTermsWithQuantifier,
                                               setup.countOfCreatedDocuments );

        documentTreeWithGlobalKnowledge.level_order_insert( null, optimalDocumentTree, 0, setup.countOfCreatedDocuments );
        documentTreeWithLocalKnowledge.level_order_insert( null, optimalDocumentTree, 0, setup.countOfCreatedDocuments );
        stressReducedDocumentTree.level_order_insert( null, optimalDocumentTree, 0, setup.countOfCreatedDocuments );
    }



    @Override
    protected Double[] createTermVector( Distribution distribution,
                                                   int maxCountOfTerms,
                                                   int maxCountOfTermsWithQuantifier ) {

        Double[] termList = new Double[maxCountOfTerms];

        for( int termsWithQuantifier = 0; termsWithQuantifier < maxCountOfTermsWithQuantifier; ) {
            int index = randomNumberGenerator.getInt( maxCountOfTerms );

            if( termList[index] == null || termList[index] == 0 ) {
                termList[index] = randomNumberGenerator.getDouble(distribution, 10 );
                termsWithQuantifier++;
            }
        }

        return termList;
    }



    private Document<Double>[] createDocuments( Distribution distribution,
                                                int maxCountOfTerms,
                                                int maxCountOfTermsWithQuantifier,
                                                int maxCountOfDocuments ) {
        Document<Double>[] documentArray = new Document[maxCountOfDocuments];

        for( int i = 0; i < maxCountOfDocuments; i++ ) {
            documentArray[i] = new Document<>( createTermVector( distribution,
                                                                 maxCountOfTerms,
                                                                 maxCountOfTermsWithQuantifier ),
                                               "Document " + i );
        }

        return documentArray;
    }
}
