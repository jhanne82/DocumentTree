package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.component.Document;
import com.github.jhanne82.documenttree.component.DocumentNode;
import com.github.jhanne82.documenttree.numbergenerator.Distribution;
import com.github.jhanne82.documenttree.numbergenerator.RandomNumberGenerator;


public class Simulation {


    private static final int MAX_COUNT_OF_CREATED_DOCUMENTS = 1000;
    private static final int MAX_COUNT_OF_TERMS_USED_TO_DEFINE_DOCUMENT = 1000;
    private static final int MAX_COUNT_OF_TERMS_WITH_QUANTIFIER = 3;
    private static final int MAX_COUNT_OF_CHILDS_PER_NODE = 3;


    public Simulation( boolean useCluster,
                       Distribution distributionDocuments,
                       Distribution distributionSearch ) {

        DocumentNode<Integer> documentTree = createDocumentTree( distributionDocuments );
    }


    private DocumentNode<Integer> createDocumentTree( Distribution distribution ) {

        Document<Integer> document = createDocument( distribution );
        DocumentNode<Integer> root = new DocumentNode<>( document );

        //createNode( root, 1, distribution ) ;
        root = add( createDocument() );

        return root;
    }


    private DocumentNode<Integer> add( Document<Integer> document ) {

        if( document != null ) {
            return new DocumentNode<>(document, add(createDocument()), add(createDocument()));
        } else {
            return new DocumentNode<>();
        }
    }


    private void createNode( DocumentNode<Integer> parent, int count, Distribution distribution ) {

        if( count < MAX_COUNT_OF_CREATED_DOCUMENTS ) {

            Document<Integer> document = createDocument( distribution );
            DocumentNode<Integer> child = new DocumentNode<>( document );
            parent.addChildLeaf( child );

            count++;

            if( parent.getChildLeaves().size() < MAX_COUNT_OF_CHILDS_PER_NODE ) {
                createNode( parent, count, distribution );
            } else {
                createNode( child, count, distribution );
            }
        }
    }


    int COUNT = 0;
    private Document<Integer> createDocument() {

        System.out.println( "COUNT : " + COUNT);

        if( COUNT < MAX_COUNT_OF_CREATED_DOCUMENTS ) {
            COUNT++;
            return createDocument( Distribution.EQUALLY );
        }

        return null;
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
