package com.github.jhanne82.documenttree.document;


import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

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



    public ResultDocumentList<T> breadthFirstSearch( int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {
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
                    node.getDocument().setTimestampOfLastSearch( searchTimeStamp );
                    resultDocumentList.add(node.getDocument(), nodeCount);
                    nodesOnNextLevel.addAll(node.getChildLeaves());
                    nodeCount++;
                }
            }
        }

        return resultDocumentList;
    }



    private int currentlyVisitedNode;
    private ResultDocumentList<T> resultDocumentList;
    public ResultDocumentList<T> depthFirstSearch( int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {
        resultDocumentList = new ResultDocumentList<>( 1 );
        currentlyVisitedNode = 0;

        depthFirstSearch( rootNode, maxVisitedNode, searchTerm, searchTimeStamp  );
        return resultDocumentList;
    }

    

    private boolean depthFirstSearch( DocumentNode<T> node, int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {

        if ( node == null) {
            return false;
        }

        if (currentlyVisitedNode == maxVisitedNode ) {
            return true;
        } else {
            node.getDocument().addRelevance( calcRelevanceOfDocument( node.getDocument().getTermList(), searchTerm ) );
            node.getDocument().setTimestampOfLastSearch( searchTimeStamp );
            resultDocumentList.add( node.getDocument(), currentlyVisitedNode );
            currentlyVisitedNode++;
        }

        return (   depthFirstSearch( node.getLeftChild(), maxVisitedNode, searchTerm, searchTimeStamp )
                || depthFirstSearch( node.getRightChild(), maxVisitedNode, searchTerm, searchTimeStamp ));
    }



    protected abstract double calcRelevanceOfDocument(T[] documentTermVector, T[] searchTermVector );



    public int repositionOfDocuments( int numberOfRelevenceCalculationToRepositiong, int timestampOfLastSearch, int treshold ) {

        ArrayList<DocumentNode<T>> nodesOnNextLevel    = new ArrayList<>();
        int countOfRepositionings = 0;

        nodesOnNextLevel.add( rootNode );

        // maxVisitedNode means no Abbruchkriterium
        while ( !nodesOnNextLevel.isEmpty() ) {

            // ChildLeaves von vorherigen Nodes werden zu aktuellen Nodes
            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
            nodesOnNextLevel.clear();

            for( DocumentNode<T> node : nodesOnCurrentLevel ) {

                if( node != null ) {
                    if (node.getDocument().getCountOfStoredRelevances() >= numberOfRelevenceCalculationToRepositiong) {

                        if ( node.getLeftChild() != null
                             && node.getLeftChild().getDocument().getCountOfStoredRelevances()
                                >= numberOfRelevenceCalculationToRepositiong
                             && node.getLeftChild().getDocument().getAverageRelevance() > node.getDocument()
                                                                                              .getAverageRelevance() ) {
                            switchDocuments( node, node.getLeftChild() );
                            countOfRepositionings++;

                        } else if ( node.getRightChild() != null
                                    && node.getRightChild().getDocument().getCountOfStoredRelevances() > numberOfRelevenceCalculationToRepositiong
                                    && node.getRightChild().getDocument().getAverageRelevance() > node.getDocument().getAverageRelevance() ) {
                            switchDocuments( node, node.getRightChild() );
                            countOfRepositionings++;
                        }
                    } else if( timestampOfLastSearch - node.getDocument().getTimestampOfLastSearch() >= treshold
                               && node.getDocument().getTimestampOfLastSearch() < node.getParent().getDocument().getTimestampOfLastSearch()) {
                        switchDocuments( node, node.getParent() );
                        countOfRepositionings++;
                    }
                    nodesOnNextLevel.addAll( node.getChildLeaves() );
                }
            }
        }
        return countOfRepositionings;
    }



    private void switchDocuments( DocumentNode<T> a, DocumentNode<T> b ) {

        Document<T> tmpDoc = a.getDocument();
        a.setDocument( b.getDocument() );
        b.setDocument( tmpDoc );

        a.getDocument().clearRelevanceBuffer();
        b.getDocument().clearRelevanceBuffer();

    }



    public DocumentNode<T> getRootNode() {
        return rootNode;
    }


}
