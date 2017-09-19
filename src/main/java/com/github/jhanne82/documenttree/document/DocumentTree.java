package com.github.jhanne82.documenttree.document;


import java.util.ArrayList;

public abstract class DocumentTree<T> {

    private DocumentNode<T> rootNode;


    public void level_order_insert( DocumentNode<T> root, Document<T>[] documents, int start, int size ) {
        int left = 2*start+1;
        int right = 2*start+2;

        if( left > size || right > size ) {
            return;
        }

        if (root == null) {
            rootNode = new DocumentNode<>( documents[start].clone() );
            root = rootNode;
        }

        if ( root.getLeftChild() == null && root.getRightChild() == null ) {
            if( left < size ) {
                root.setLeftChild( new DocumentNode<>( documents[left ].clone() ));
            }
            if( right < size ) {
                root.setRightChild( new DocumentNode<>( documents[right].clone() ) );
            }
        }

        level_order_insert( root.getLeftChild(), documents, left, size );
        level_order_insert( root.getRightChild(), documents, right, size );
    }



    public ResultDocumentList<T> breadthFirstSearch( int maxVisitedNode, T[] searchTerm ) {
        ResultDocumentList<T> resultDocumentList = new ResultDocumentList<>( 1 );
        ArrayList<DocumentNode<T>> nodesOnCurrentLevel = new ArrayList<>();
        ArrayList<DocumentNode<T>> nodesOnNextLevel    = new ArrayList<>();

        nodesOnNextLevel.add( rootNode );

        int nodeCount = 0;

        // maxVisitedNode means no Abbruchkriterium
        while ( !nodesOnNextLevel.isEmpty() && ( nodeCount < maxVisitedNode ) ) {
            nodesOnCurrentLevel.clear();
            
            // ChildLeaves von vorherigen Nodes werden zu aktuellen Nodes
            nodesOnCurrentLevel = (ArrayList<DocumentNode<T>>) nodesOnNextLevel.clone();
            nodesOnNextLevel.clear();

            for( DocumentNode<T> node : nodesOnCurrentLevel ) {
                if( nodeCount == maxVisitedNode ) {
                    break;
                }
                if( node != null ) {
                    node.getDocument().addRelevance(calcRelevanceOfDocument(node.getDocument().getTermList(), searchTerm));
                    resultDocumentList.add(node.getDocument());
                    nodesOnNextLevel.addAll(node.getChildLeaves());
                    nodeCount++;
                }
            }
        }

        return resultDocumentList;
    }


    private static int currentlyVisitedNode = 0;
    public ResultDocumentList<T> depthFirstSearch( int maxVisitedNode, T[] searchTerm ) {

        currentlyVisitedNode = 0;
        ResultDocumentList<T> resultDocumentList = new ResultDocumentList<>( 1 );
        depthFirstSearch( rootNode, maxVisitedNode, resultDocumentList, searchTerm );
        return resultDocumentList;
    }


    private boolean depthFirstSearch( DocumentNode<T> node, int maxVisitedNode, ResultDocumentList<T> resultDocumentList, T[] searchTerm ) {

        if ( node == null) {
            return false;
        }

        if (currentlyVisitedNode == maxVisitedNode ) {
            return true;
        } else {
            node.getDocument().addRelevance( calcRelevanceOfDocument( node.getDocument().getTermList(), searchTerm ) );
            resultDocumentList.add( node.getDocument() );
            currentlyVisitedNode++;
        }

        return (   depthFirstSearch( node.getLeftChild(), maxVisitedNode, resultDocumentList, searchTerm )
                || depthFirstSearch( node.getRightChild(), maxVisitedNode, resultDocumentList, searchTerm ));
    }


    protected abstract double calcRelevanceOfDocument(T[] documentTermVector, T[] searchTermVector );



    public void repositioning( int numberOfRelevenceCalculationToRepositiong ) {
        repositioning( rootNode, numberOfRelevenceCalculationToRepositiong );
    }


    private boolean repositioning( DocumentNode<T> node, int numberOfRelevenceCalculationToRepositiong ) {

        if ( node == null) {
            return false;
        }

        if( node.getDocument().getCountOfStoredRelevances() >= numberOfRelevenceCalculationToRepositiong ) {

            DocumentNode<T> leftChild = node.getLeftChild();
            if(    leftChild != null
                && node.getDocument().getAverageRelevance() < leftChild.getDocument().getAverageRelevance() ) {
                switchNodes( node, leftChild );

            } else {
                DocumentNode<T> rightChild = node.getLeftChild();

                if(    rightChild != null
                    && node.getDocument().getAverageRelevance() < rightChild.getDocument().getAverageRelevance() ) {
                    switchNodes( node, rightChild );
                }
            }
        }


        return    repositioning( node.getLeftChild(), numberOfRelevenceCalculationToRepositiong )
               || repositioning( node.getRightChild(), numberOfRelevenceCalculationToRepositiong );
    }


    private void switchNodes( DocumentNode<T> a, DocumentNode<T> b ) {
        DocumentNode<T> tmpParent = a.getParent();
        b.setParent( tmpParent );
        a.setParent( b );

        DocumentNode<T> tmpRightChild = a.getRightChild();
        DocumentNode<T> tmpLeftChild  = a.getLeftChild();

        a.setRightChild( b.getRightChild() );
        b.setRightChild( tmpRightChild );

        a.setLeftChild( b.getLeftChild() );
        b.setLeftChild( tmpLeftChild );

        a.getDocument().clearRelevanceBuffer();
        b.getDocument().clearRelevanceBuffer();

    }


}
