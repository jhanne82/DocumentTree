package com.github.jhanne82.documenttree;


import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentList;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * Generic abstract class which implements a binary tree to store {@link com.github.jhanne82.documenttree.document.Document }
 * This implementation provides methods to
 * <li>fill the tree by a given list of Documents</li>
 * <li>Breadth First Search</li>
 * <li>Depth First Search</li>
 * <li>Random Walker Search</li>
 * <li>reposition of documents</li>
 *
 * @param <T> the type of the elements of terms describing the document.
 */
public abstract class DocumentTree<T> {


    
    private DocumentNode<T> rootNode;
    private DocumentList<T> resultDocumentList;
    private int             countOfSearchedDocuments;



    /**
     * Calculates the relevance of the document by the given search term.
     *
     * @param documentTermVector defines the vetor of the term
     * @param searchTermVector defines the term vector of the search.
     * @return the calculated relevance
     */
    protected abstract double calcRelevanceOfDocument(T[] documentTermVector, T[] searchTermVector );



    /**
     * Returns the root node of the tree.
     * @return the root node.
     */
    public DocumentNode<T> getRootNode() {
        return rootNode;
    }



    /**
     * Recursive method to create a tree from an array of Documents. The tree will be placed under a given node.
     * If root is null a root node will be generated.
     *
     * @param root defines the node under which the tree should be placed.
     * @param documents defines the array of documents which should be inserted.
     * @param start defines the index from which the insert should start from the given array.
     * @param size defines the number of the documents which should be added to the tree. Size should be less or equal than the size of the document array.
     */
    public void level_order_insert( DocumentNode<T> root, Document<T>[] documents, int start, int size ) {
        int left = 2*start+1;
        int right = 2*start+2;

        // stopp insert if defined number of created nodes for the tree is reached
        if( left > size || right > size ) {
            return;
        }

        if (root == null) {
            rootNode = createNode( documents[start] );
            root = rootNode;
        }

        // if node has no child nodes, add new nodes
        if ( root.getLeftChild() == null && root.getRightChild() == null ) {
            // handle border case
            if( left < size ) {
                root.setLeftChild( createNode( documents[left] ) );
            }
            if( right < size ) {
                root.setRightChild( createNode( documents[right] ) );
            }
        }

        // recursive call
        level_order_insert( root.getLeftChild(), documents, left, size );
        level_order_insert( root.getRightChild(), documents, right, size );
    }



    /**
     * Method which will perform a search using Breadth First.
     * Breadth-first search (BFS) is an algorithm for traversing or searching tree or graph data structures.
     * It starts at the tree root and explores the neighbor nodes first, before moving to the next level neighbours.
     *
     * The search will be performed until maxVisitedNodes are searched.
     *
     * @param maxVisitedNode defines how many nodes will be searched
     * @param searchTerm defines the term vector of the search request
     * @param searchTimeStamp defines the given timestamp of the search which will be stored on the search document
     * @return a list of documents sorted by relevance
     * @see DocumentList
     */
    public DocumentList<T> breadthFirstSearch(int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {
        resultDocumentList = new DocumentList<>( 1 );

        List<DocumentNode<T>> nodesOnNextLevel = new ArrayList<>();

        nodesOnNextLevel.add( rootNode );

        countOfSearchedDocuments = 0;

        while ( !nodesOnNextLevel.isEmpty() ) {

            // child nodes from the previous while loop are now the nodes to be searched
            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
            nodesOnNextLevel = new ArrayList<>();

            for( DocumentNode<T> node : nodesOnCurrentLevel ) {
                // if maximum number of nodes are searched the search will be terminated
                if( countOfSearchedDocuments == maxVisitedNode ) {
                    return resultDocumentList;
                }
                if( node != null ) {
                    node.getDocument().addRelevance(calcRelevanceOfDocument(node.getDocument().getTermVector(), searchTerm));
                    node.getDocument().setTimestampOfLatestSearch( searchTimeStamp );
                    resultDocumentList.add( node.getDocument(), countOfSearchedDocuments );

                    // set child nodes to list of nodes which should be search in next while loop
                    nodesOnNextLevel.addAll(node.getChildLeaves());
                    countOfSearchedDocuments++;
                }
            }
        }

        return resultDocumentList;
    }



    /**
     * Method which will perform a search using Depth First.
     * Depth-first search (DFS) is an algorithm for traversing or searching tree.
     * One starts at the root and explores as far as possible along each branch before backtracking.
     *
     * The search will be performed until maxVisitedNodes are searched.
     *
     * @param maxVisitedNode defines how many nodes will be searched
     * @param searchTerm defines the term vector of the search request
     * @param searchTimeStamp defines the given timestamp of the search which will be stored on the search document
     * @return a list of documents sorted by relevance
     * @see DocumentList
     */
    public DocumentList<T> depthFirstSearch(int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {
        resultDocumentList = new DocumentList<>( 1 );
        countOfSearchedDocuments = 0;

        // call method which will search recursively
        depthFirstSearch( rootNode, maxVisitedNode, searchTerm, searchTimeStamp  );
        return resultDocumentList;
    }


    
    // method which implements the search in an recursive way
    private boolean depthFirstSearch( DocumentNode<T> node, int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {

        // end of branch is reached
        if ( node == null) {
            return false;
        }

        // if maximum number of nodes are searched the search will be terminated
        if ( countOfSearchedDocuments == maxVisitedNode ) {
            return true;
        } else {
            node.getDocument().addRelevance( calcRelevanceOfDocument( node.getDocument().getTermVector(), searchTerm ) );
            node.getDocument().setTimestampOfLatestSearch( searchTimeStamp );
            resultDocumentList.add( node.getDocument(), countOfSearchedDocuments );
            countOfSearchedDocuments++;
        }

        // search as long as possible on the left branch
        return (   depthFirstSearch( node.getLeftChild(), maxVisitedNode, searchTerm, searchTimeStamp )
                || depthFirstSearch( node.getRightChild(), maxVisitedNode, searchTerm, searchTimeStamp ));
    }



    /**
     * Method which will perform a search by walk randomly through the tree.
     * After a node was searched it will be randomly defined if the next node
     * will be considered by breadth or depth first search.
     *
     * The search will be performed until maxVisitedNodes are searched.
     *
     * @param maxVisitedNode defines how many nodes will be searched
     * @param searchTerm defines the term vector of the search request
     * @param searchTimeStamp defines the given timestamp of the search which will be stored on the search document
     * @return a list of documents sorted by relevance
     * @see DocumentList
     */
    public DocumentList<T> randomWalkSearch(int maxVisitedNode, T[] searchTerm, int searchTimeStamp ) {
        resultDocumentList = new DocumentList<>( 1 );
        countOfSearchedDocuments = 0;

        DocumentNode<T> currentlyConsideredNode = rootNode;

        while ( currentlyConsideredNode != null && countOfSearchedDocuments < maxVisitedNode ){
            countOfSearchedDocuments++;
            currentlyConsideredNode.getDocument().addRelevance( calcRelevanceOfDocument( currentlyConsideredNode.getDocument().getTermVector(), searchTerm ) );
            currentlyConsideredNode.getDocument().setTimestampOfLatestSearch( searchTimeStamp );
            resultDocumentList.add( currentlyConsideredNode.getDocument(), countOfSearchedDocuments );
            currentlyConsideredNode = getNextRandomNode( currentlyConsideredNode, searchTimeStamp );

        }
        return resultDocumentList;
    }



    // Returns the next node. It will by randomly decided if this node is a child node (Depth First)
    // from the given node or a neighbour (Breadth First).
    private DocumentNode<T> getNextRandomNode( DocumentNode<T> node, int searchTimeStamp ) {

        DocumentNode<T> tmpNode = node;
        DocumentNode<T> returnNode;

        boolean useBreadthFirstSearch = (new Random()).nextBoolean();

        if( useBreadthFirstSearch ){
            returnNode = getNextPossibleChildNodeFromBreadth( node, searchTimeStamp );
            // if  returnNode is null, loop on the level from the parent node to find a next node
            while( returnNode == null && tmpNode.getParent() != null ) {
                returnNode = getNextPossibleChildNodeFromBreadth( tmpNode.getParent(), searchTimeStamp );
                tmpNode = tmpNode.getParent();
            }
            return returnNode;

        } else {
            returnNode = getNextPossibleChildNodeFromDepth( node, searchTimeStamp );
            // if  returnNode is null, look on the level from the parent node to find a next node
            while( returnNode == null && tmpNode.getParent() != null ) {
                returnNode = getNextPossibleChildNodeFromDepth( tmpNode.getParent(), searchTimeStamp );
                tmpNode = tmpNode.getParent();
            }
            return returnNode;
        }
    }



    // search via breadth first the next possible node which was not already searched
    private DocumentNode<T> getNextPossibleChildNodeFromBreadth( DocumentNode<T> node, int searchTimeStamp ) {

        List<DocumentNode<T>> nodesOnNextLevel = new ArrayList<>();

        nodesOnNextLevel.add( node );

        while ( !nodesOnNextLevel.isEmpty() ) {
            // child nodes from the previous while loop are now the nodes to be searched
            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
            nodesOnNextLevel = new ArrayList<>();

            for( DocumentNode<T> nodeOnCurrentLevel : nodesOnCurrentLevel ) {
                // if node was not searched it will be returned
                if( nodeOnCurrentLevel.getDocument().getTimestampOfLatestSearch() != searchTimeStamp ) {
                    return nodeOnCurrentLevel;
                }
                nodesOnNextLevel.addAll(nodeOnCurrentLevel.getChildLeaves());
            }
        }
        // if no node could be found which was not searched already, it will be returned null
        return null;
    }


    // search via depth first the next possible node which was not already searched
    private DocumentNode<T> getNextPossibleChildNodeFromDepth( DocumentNode<T> node, int searchTimeStamp ) {

        if ( node == null) {
            return null;
        }
        if( node.getDocument().getTimestampOfLatestSearch() != searchTimeStamp ) {
            return node;
        }
        DocumentNode<T> nextNode = getNextPossibleChildNodeFromDepth( node.getLeftChild(), searchTimeStamp );

        if( nextNode == null ) {
            nextNode = getNextPossibleChildNodeFromDepth( node.getRightChild(), searchTimeStamp );
        }

        return nextNode;
    }



    /**
     * Will do a reposition of the documents in the tree.
     * As first step the document will be switched related to the average relevance of the last searches.
     * As second step these documents can be switched which were not searched for a specified time (defined via threshold).
     *
     * @param numberOfRelevenceCalculationToRepositiong defines the number of required searches on a document to perform a document switch depending on the average relevance.
     * @param timestampOfLastSearch defines the timestamp of the last perfomed search on the tree
     * @param threshold defines the limit for searches which were NOT performed on the documents which allows them to switch them independent from the average relevance
     * @return the number of documents which were switched
     */
    public int repositionOfDocuments( int numberOfRelevenceCalculationToRepositiong, int timestampOfLastSearch, int threshold ) {

        int countOfRepositionings = repositionOfDocumentsDependingOnRelevance( numberOfRelevenceCalculationToRepositiong );
        countOfRepositionings += repositionOfDocumentsWhenThresholdIsReached( numberOfRelevenceCalculationToRepositiong,
                                                                              timestampOfLastSearch,
                                                                              threshold );

        return countOfRepositionings ;
    }



    private int repositionOfDocumentsDependingOnRelevance( int numberOfRelevanceCalculationToRepositioning ) {

        List<DocumentNode<T>> nodesOnNextLevel = new ArrayList<>();
        int requiredRepositionings = 0;

        nodesOnNextLevel.add( rootNode );

        while ( !nodesOnNextLevel.isEmpty() ) {
            // child nodes from the previous while loop are now the nodes to be searched
            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
            nodesOnNextLevel = new ArrayList<>();

            for( DocumentNode<T> nodeOnCurrentLevel : nodesOnCurrentLevel ) {

                // node can only be switched if the required count of stored relevance values is reached
                if (nodeOnCurrentLevel.getDocument().getCountOfStoredRelevances() >= numberOfRelevanceCalculationToRepositioning) {

                    Document<T> leftDocument = nodeOnCurrentLevel.getLeftChild() != null ? nodeOnCurrentLevel.getLeftChild()
                            .getDocument()
                            : null;
                    Document<T> rigthDocument = nodeOnCurrentLevel.getRightChild() != null ? nodeOnCurrentLevel.getRightChild()
                            .getDocument()
                            : null;

                    // switch current node with child node if child node a average relevance which is greater than the
                    // average relevance of the current node
                    if (leftDocument != null
                            && leftDocument.getCountOfStoredRelevances() >= numberOfRelevanceCalculationToRepositioning
                            && leftDocument.getAverageRelevance() > nodeOnCurrentLevel.getDocument().getAverageRelevance()) {
                        numberOfRelevanceCalculationToRepositioning++;
                        switchDocuments(nodeOnCurrentLevel, nodeOnCurrentLevel.getLeftChild());

                    } else if (rigthDocument != null
                            && rigthDocument.getCountOfStoredRelevances() >= numberOfRelevanceCalculationToRepositioning
                            && rigthDocument.getAverageRelevance() > nodeOnCurrentLevel.getDocument().getAverageRelevance()) {
                        requiredRepositionings++;
                        switchDocuments(nodeOnCurrentLevel, nodeOnCurrentLevel.getRightChild());
                    }
                }
                nodesOnNextLevel.addAll(nodeOnCurrentLevel.getChildLeaves());
            }
        }
        return requiredRepositionings;
    }



    private int repositionOfDocumentsWhenThresholdIsReached( int numberOfRelevenceCalculationToRepositiong,
                                                             int timestampOfLastSearch,
                                                             int threshold ) {

        List<DocumentNode<T>> nodesOnNextLevel = new ArrayList<>();
        int requiredRepositionings = 0;

        nodesOnNextLevel.add( rootNode );

        while ( !nodesOnNextLevel.isEmpty() ) {
            // child nodes from the previous while loop are now the nodes to be searched
            List<DocumentNode<T>> nodesOnCurrentLevel = ImmutableList.copyOf( nodesOnNextLevel );
            nodesOnNextLevel = new ArrayList<>();

            for( DocumentNode<T> nodeOnCurrentLevel : nodesOnCurrentLevel ) {

                // node can only be switched if less than the required relevance values are stored on the document
                // and the document was not considered for the last <threshold> searches
                if(    nodeOnCurrentLevel.getDocument().getCountOfStoredRelevances() < numberOfRelevenceCalculationToRepositiong
                    && nodeOnCurrentLevel.getDocument().getTimestampOfLatestSearch() <= ( timestampOfLastSearch-threshold ) ) {

                    Document<T> parentDocument = nodeOnCurrentLevel.getParent() != null ? nodeOnCurrentLevel.getParent()
                                                                                                             .getDocument()
                                                                                        : null;

                    // nodes can be switched if the parentNode was considered for a later search than the latest search
                    if(    parentDocument != null
                        && parentDocument.getTimestampOfLatestSearch() > (timestampOfLastSearch-threshold)
                        && parentDocument.getTimestampOfLatestSearch() > nodeOnCurrentLevel.getDocument()
                                                                                           .getTimestampOfLatestSearch() ) {
                        requiredRepositionings++;
                        switchDocuments( nodeOnCurrentLevel, nodeOnCurrentLevel.getParent() );
                    }
                }
                nodesOnNextLevel.addAll(nodeOnCurrentLevel.getChildLeaves());
            }
        }
        return requiredRepositionings;
    }



    // just swith the documents and clear the relevance buffer
    private void switchDocuments( DocumentNode<T> a, DocumentNode<T> b ) {

        Document<T> tmpDoc = a.getDocument();
        a.setDocument( b.getDocument() );
        b.setDocument( tmpDoc );

        a.getDocument().clearRelevanceBuffer();
        b.getDocument().clearRelevanceBuffer();
    }



    private DocumentNode<T> createNode( Document<T> document ) {

        return new DocumentNode<>( new Document<>( document.getTermVector(),
                                                   document.getDocumentName() ) );
    }


}
