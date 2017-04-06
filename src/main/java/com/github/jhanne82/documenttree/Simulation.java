package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.component.Document;
import com.github.jhanne82.documenttree.component.DocumentNode;
import com.github.jhanne82.documenttree.numbergenerator.Distribution;
import com.github.jhanne82.documenttree.numbergenerator.RandomNumberGenerator;



public class Simulation {


    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;


    public Simulation( boolean useCluster,
                       Distribution distributionDocuments,
                       Distribution distributionSearch ) {

        DocumentNode<Integer> documentTree = createDocumentTree( distributionDocuments );
    }


    private DocumentNode<Integer> createDocumentTree( Distribution distribution ) {

        DocumentNode<Integer> root = new DocumentNode<>( createDocument( distribution ) );

        Document document = createDocument( distribution );

        do {
            insert( null, root, document );
            document = createDocument( distribution );

        } while ( document != null );

        return root;
    }



    private void insert( DocumentNode<Integer> parent, DocumentNode<Integer> currentNode, Document<Integer> document ) {

        if( currentNode == null ) {
            currentNode= new DocumentNode<>( document );

            if( parent != null ){
                if( parent.isLeaf() || parent.getLeftChild() == null ) {
                    parent.addLeftChildLeaf( currentNode );
                } else {
                    parent.addRightChildLeaf( currentNode );
                }

            }
            return;
        }

        if( currentNode.getDocument().getNumber() > document.getNumber() ) {
            insert( currentNode, currentNode.getLeftChild(), document );
        } else {
            insert( currentNode, currentNode.getRightChild(), document );
        }
    }




    int COUNT = 0;
    private Document<Integer> createDocument( Distribution distribution ) {

        if( COUNT >= MAX_COUNT_OF_CREATED_DOCUMENTS ) {
            return null;
        }

        Integer[] termVector = new Integer[MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT];

        for( int i = 0; i < MAX_COUNT_OF_TERMS_WITH_QUANTIFIER ; ) {
            int index = (new RandomNumberGenerator( distribution,
                                                    MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT ) ).getInt();
            if ( termVector[ index ] == null ) {
                termVector[index] = 1;
                i++;
            }
        }

        COUNT++;
        return new Document<>( termVector, (new RandomNumberGenerator( distribution,
                                                                       MAX_COUNT_OF_CREATED_DOCUMENTS ) ).getInt() );
    }



}
