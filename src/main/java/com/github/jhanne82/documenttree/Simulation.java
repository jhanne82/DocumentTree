package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.document.numberdocument.NumberDocumentTree;
import com.github.jhanne82.documenttree.simulation.Distribution;
import com.github.jhanne82.documenttree.utils.RandomNumberGenerator;


public class Simulation {


    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;

    private Document<Integer>[] referenceTree;
    private DocumentTree globalKnowledge = new NumberDocumentTree();
    private DocumentTree localKnowledge  = new NumberDocumentTree();



    public Simulation( boolean useCluster,
                       Distribution distributionDocuments,
                       Distribution distributionSearch ) {

        createDocumentTree( distributionDocuments );
        System.out.println( globalKnowledge.breadthFirstSearch( MAX_COUNT_OF_CREATED_DOCUMENTS, createDocument( Distribution.EQUALLY, 0 ).getTermVector() ).toString() );
        System.out.println( globalKnowledge.depthFirstSearch( MAX_COUNT_OF_CREATED_DOCUMENTS -100, createDocument(Distribution.EQUALLY, 0).getTermVector()).toString() );
    }


    private void createDocumentTree( Distribution distribution ) {
        referenceTree = createDocuments( MAX_COUNT_OF_CREATED_DOCUMENTS, distribution );

        globalKnowledge.level_order_insert( globalKnowledge.rootNode, referenceTree, 0, MAX_COUNT_OF_CREATED_DOCUMENTS );
        localKnowledge.level_order_insert(  localKnowledge.rootNode, referenceTree, 0, MAX_COUNT_OF_CREATED_DOCUMENTS );
    }



    private Document<Integer>[] createDocuments( int size, Distribution distribution ) {

        Document<Integer>[] documents = new Document[ size ];

        for( int i = 0; i < size; i++ ) {
            documents[i] = createDocument( distribution, i );
        }

        return documents;
    }



    private Document<Integer> createDocument( Distribution distribution, int counter ) {

        Integer[] termVector = new Integer[MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT];
        for( int i = 0; i < MAX_COUNT_OF_TERMS_WITH_QUANTIFIER ; ) {
            int index = (new RandomNumberGenerator( distribution,
                                                    MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT ) ).getInt();
            if ( termVector[ index ] == null ) {
                // random number + 1 to avoid 0 values and max value is exclusive
                termVector[index] = (new RandomNumberGenerator( distribution,
                                                                10 ) ).getInt() + 1;
                i++;
            }
        }
        return new Document<>( termVector, "Document " + counter  );
    }



}
