package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.component.Document;
import com.github.jhanne82.documenttree.component.DocumentTree;
import com.github.jhanne82.documenttree.numbergenerator.Distribution;
import com.github.jhanne82.documenttree.numbergenerator.RandomNumberGenerator;


public class Simulation {


    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;

    private Document<Integer>[] referenceTree;
    private DocumentTree<Integer> globalKnowledge = new DocumentTree<>();
    private DocumentTree<Integer> localKnowledge  = new DocumentTree<>();



    public Simulation( boolean useCluster,
                       Distribution distributionDocuments,
                       Distribution distributionSearch ) {

        createDocumentTree( distributionDocuments );
    }


    private void createDocumentTree( Distribution distribution ) {
        referenceTree = createDocuments( MAX_COUNT_OF_CREATED_DOCUMENTS, distribution );

        globalKnowledge.level_order_insert( globalKnowledge.rootNode, referenceTree, 0, MAX_COUNT_OF_CREATED_DOCUMENTS );
        localKnowledge.level_order_insert(  localKnowledge.rootNode, referenceTree, 0, MAX_COUNT_OF_CREATED_DOCUMENTS );
    }



    private Document<Integer>[] createDocuments( int size, Distribution distribution ) {

        Document<Integer>[] documents = new Document[ size ];

        for( int i = 0; i < size; i++ ) {
            documents[i] = createDocument( distribution );
        }

        return documents;
    }



    private Document<Integer> createDocument( Distribution distribution ) {

        Integer[] termVector = new Integer[MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT];
        for( int i = 0; i < MAX_COUNT_OF_TERMS_WITH_QUANTIFIER ; ) {
            int index = (new RandomNumberGenerator( distribution,
                                                    MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT ) ).getInt();
            if ( termVector[ index ] == null ) {
                termVector[index] = 1;
                i++;
            }
        }
        return new Document<>( termVector );
    }



}
