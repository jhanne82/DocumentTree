package com.github.jhanne82.documenttree.document.numberdocument;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentNode;
import com.github.jhanne82.documenttree.document.DocumentTree;
import com.github.jhanne82.documenttree.utils.EulerianDistance;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.spy;


public class NumberDocumentTreeTest {

    DocumentTree tree;
    Document[]   documents;

    @Before
    public void setup() {

        tree = spy( new NumberDocumentTree() );
        documents = new Document[]{ new Document<>( new Double[]{0.3, 0.1, 0.2}, "Doc1" ),
                                    new Document<>( new Double[]{0.3, 0.3, 0.2}, "Doc2" ),
                                    new Document<>( new Double[]{0.3, 0.2, 0.2}, "Doc3" ),
                                    new Document<>( new Double[]{0.3, 0.0, 0.2}, "Doc4" ),
                                    new Document<>( new Double[]{0.0, 0.1, 0.0}, "Doc5" )};


    }



    @Test
    public void orderInsert() {
        tree.level_order_insert( null, documents, 0, documents.length );

        DocumentNode node = tree.getRootNode();
        assertEquals( "Doc1", node.getDocument().getDocumentName() );
        assertEquals( "Doc2", node.getLeftChild().getDocument().getDocumentName() );
        assertEquals( "Doc3", node.getRightChild().getDocument().getDocumentName() );
        assertEquals( "Doc4", node.getLeftChild().getLeftChild().getDocument().getDocumentName() );
        assertEquals( "Doc5", node.getLeftChild().getRightChild().getDocument().getDocumentName() );
        assertNull( node.getRightChild().getLeftChild() );
        assertNull( node.getRightChild().getRightChild() );
        assertNull( node.getRightChild().getRightChild() );
        assertNull( node.getLeftChild().getLeftChild().getLeftChild() );
        assertNull( node.getLeftChild().getLeftChild().getRightChild() );
        assertNull( node.getLeftChild().getRightChild().getLeftChild() );
        assertNull( node.getLeftChild().getRightChild().getRightChild() );
    }



    @Test
    public void depthFirstSeach(){
        tree.level_order_insert( null, documents, 0, documents.length );

        Double[] searchVector = new Double[]{ 0.1, 0.1, 0.1 };

        tree.depthFirstSearch( 5, searchVector, 20 );

        assertEquals( "Doc1", tree.getRootNode().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc2", tree.getRootNode().getLeftChild().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getLeftChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getLeftChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getLeftChild().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc3", tree.getRootNode().getRightChild().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getRightChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getRightChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getRightChild().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc4", tree.getRootNode().getLeftChild().getLeftChild().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getLeftChild().getLeftChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getLeftChild().getLeftChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getLeftChild().getLeftChild().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc5", tree.getRootNode().getLeftChild().getRightChild().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getLeftChild().getRightChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getLeftChild().getRightChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getLeftChild().getRightChild().getDocument().getLastCalculatedRelevance() );
    }



    @Test
    public void depthFirstSearch2() {


        tree.level_order_insert( null, documents, 0, documents.length );

        Double[] searchVector = new Double[]{ 0.1, 0.1, 0.1 };

        tree.depthFirstSearch( 3, searchVector, 20 );

        assertEquals( "Doc1", tree.getRootNode().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getDocument().getTimestampOfLastSearch() );

        assertEquals( "Doc2", tree.getRootNode().getLeftChild().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getLeftChild().getDocument().getTimestampOfLastSearch() );

        assertEquals( "Doc3", tree.getRootNode().getRightChild().getDocument().getDocumentName() );
        assertEquals( 0, tree.getRootNode().getRightChild().getDocument().getTimestampOfLastSearch() );

        assertEquals( "Doc4", tree.getRootNode().getLeftChild().getLeftChild().getDocument().getDocumentName() );
        assertEquals( 20, tree.getRootNode().getLeftChild().getLeftChild().getDocument().getTimestampOfLastSearch() );

        assertEquals( "Doc5", tree.getRootNode().getLeftChild().getRightChild().getDocument().getDocumentName() );
        assertEquals( 0, tree.getRootNode().getLeftChild().getRightChild().getDocument().getTimestampOfLastSearch() );

    }



    @Test
    public void breathFirstSearch(){
        tree.level_order_insert( null, documents, 0, documents.length );

        Double[] searchVector = new Double[]{ 0.2, 0.15, 0.0 };


        tree.breadthFirstSearch( 5, searchVector, 25 );

        assertEquals( "Doc1", tree.getRootNode().getDocument().getDocumentName() );
        assertEquals( 25, tree.getRootNode().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc2", tree.getRootNode().getLeftChild().getDocument().getDocumentName() );
        assertEquals( 25, tree.getRootNode().getLeftChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getLeftChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getLeftChild().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc3", tree.getRootNode().getRightChild().getDocument().getDocumentName() );
        assertEquals( 25, tree.getRootNode().getRightChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getRightChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getRightChild().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc4", tree.getRootNode().getLeftChild().getLeftChild().getDocument().getDocumentName() );
        assertEquals( 25, tree.getRootNode().getLeftChild().getLeftChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getLeftChild().getLeftChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getLeftChild().getLeftChild().getDocument().getLastCalculatedRelevance() );

        assertEquals( "Doc5", tree.getRootNode().getLeftChild().getRightChild().getDocument().getDocumentName() );
        assertEquals( 25, tree.getRootNode().getLeftChild().getRightChild().getDocument().getTimestampOfLastSearch() );
        assertEquals( EulerianDistance.calcRelevance( ( Double[] )tree.getRootNode().getLeftChild().getRightChild().getDocument().getTermList(),
                                                      searchVector ),
                      tree.getRootNode().getLeftChild().getRightChild().getDocument().getLastCalculatedRelevance() );

    }

}
