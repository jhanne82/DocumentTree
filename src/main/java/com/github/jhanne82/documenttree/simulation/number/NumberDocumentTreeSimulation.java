package com.github.jhanne82.documenttree.simulation.number;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.simulation.Distribution;
import com.github.jhanne82.documenttree.simulation.DocumentTreeSimulation;
import com.github.jhanne82.documenttree.simulation.utils.SimulationSetup;
import com.github.jhanne82.documenttree.tree.DocumentTree;
import com.github.jhanne82.documenttree.tree.number.NumberDocumentTree;
import com.github.jhanne82.documenttree.utils.EulerianDistance;
import com.github.jhanne82.documenttree.utils.RandomNumberGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NumberDocumentTreeSimulation
    extends DocumentTreeSimulation <Double> {

    private RandomNumberGenerator randomNumberGenerator;
    private Map<Double, Double>   clusterMap;

    public NumberDocumentTreeSimulation() {
        super();
        randomNumberGenerator = new RandomNumberGenerator();
    }


    @Override
    protected Document<Double> searchOnOptimalDocumentTree( Double[] searchTermVector ) {

        Document<Double> bestMatch = null;

        for ( Document<Double> document : optimalDocumentTree ) {

            document.addRelevance( EulerianDistance.calcRelevance( document.getTermList(), searchTermVector ) );

            if(    bestMatch == null
                   || document.getLastCalculatedRelevance().compareTo( bestMatch.getLastCalculatedRelevance() ) > 0 ) {
                bestMatch = document;
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
                                               setup.countOfCreatedDocuments,
                                               setup.cluster);

        List<Thread> threads = Arrays.asList(
                new CreateTreeThread( documentTreeWithGlobalKnowledge, optimalDocumentTree, setup ),
                new CreateTreeThread( documentTreeWithLocalKnowledge,  optimalDocumentTree, setup),
                new CreateTreeThread( stressReducedDocumentTree,       optimalDocumentTree, setup )
        );

        for( Thread thread : threads ) {
            try {
                thread.start();
                thread.join();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
        /*
        documentTreeWithGlobalKnowledge.level_order_insert( null, optimalDocumentTree, 0, setup.countOfCreatedDocuments );
        documentTreeWithLocalKnowledge.level_order_insert( null, optimalDocumentTree, 0, setup.countOfCreatedDocuments );
        stressReducedDocumentTree.level_order_insert( null, optimalDocumentTree, 0, setup.countOfCreatedDocuments );*/
    }



    @Override
    protected Double[] createTermVector( Distribution distribution,
                                         int maxCountOfTerms,
                                         int maxCountOfTermsWithQuantifier,
                                         boolean cluster ) {

        if( cluster && clusterMap == null ) {
            createClusterMap();
        }

        Double[] termList = new Double[maxCountOfTerms];

        for( int termsWithQuantifier = 0; termsWithQuantifier < maxCountOfTermsWithQuantifier; ) {
            int index = randomNumberGenerator.getInt( maxCountOfTerms );

            if( termList[index] == null || termList[index] == 0 ) {
                double term = randomNumberGenerator.getDouble(distribution, 10 );
                if( cluster && clusterMap.containsKey( term ) ) {
                    term = clusterMap.get( term );
                }
                termList[index] = term;
                termsWithQuantifier++;
            }
        }

        return termList;
    }


    private void createClusterMap() {
        clusterMap = new HashMap<>();

        for( int i = 0; i< 3; i++ ) {
            double bunch = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );

            for (int j = 0; j < 3; j++ ) {
                double singleItem = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );
                while( singleItem == bunch ) {
                    singleItem = randomNumberGenerator.getDouble( Distribution.EQUALLY, 10 );
                }
                clusterMap.put( singleItem, bunch );
            }
        }
    }



    private Document<Double>[] createDocuments( Distribution distribution,
                                                int maxCountOfTerms,
                                                int maxCountOfTermsWithQuantifier,
                                                int maxCountOfDocuments,
                                                boolean cluster ) {
        Document<Double>[] documentArray = new Document[maxCountOfDocuments];

        for( int i = 0; i < maxCountOfDocuments; i++ ) {
            documentArray[i] = new Document<>( createTermVector( distribution,
                                                                 maxCountOfTerms,
                                                                 maxCountOfTermsWithQuantifier,
                                                                 cluster ),
                                               "Document " + i );
        }

        return documentArray;
    }



    private class CreateTreeThread
        extends Thread {

        DocumentTree<Double> tree;
        Document<Double>[] documents;
        SimulationSetup setup;

        CreateTreeThread( DocumentTree<Double> tree, Document<Double>[] documents, SimulationSetup setup ) {
            this.tree = tree;
            this.documents = documents;
            this.setup = setup;
        }


        @Override
        public void run() {
            tree.level_order_insert( null, documents, 0, setup.countOfCreatedDocuments );
        }
    }
}
