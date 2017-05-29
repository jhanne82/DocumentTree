package com.github.jhanne82.documenttree.component;

import com.github.jhanne82.documenttree.utils.EulerianDistance;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DocumentTree<T> {

    public DocumentNode<T> rootNode;


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
        ResultDocumentList<T> resultDocumentList = new ResultDocumentList<>();
        ArrayList<DocumentNode<T>> nodesOnCurrentLevel = new ArrayList<>();
        ArrayList<DocumentNode<T>> nodesOnNextLevel    = new ArrayList<>();

        nodesOnNextLevel.add( rootNode );

        int nodeCount = 0;

        while ( nodeCount < maxVisitedNode ) {
            nodesOnCurrentLevel.clear();
            
            // ChildLeaves von vorherigen Nodes werden zu aktuellen Nodes
            nodesOnCurrentLevel = (ArrayList<DocumentNode<T>>) nodesOnNextLevel.clone();
            nodesOnNextLevel.clear();

            if (nodesOnCurrentLevel.isEmpty()) {
                break;
            }

            for( DocumentNode<T> node : nodesOnCurrentLevel ) {
                if( nodeCount == maxVisitedNode ) {
                    break;
                }
                BigDecimal euler = EulerianDistance.calEulerianDistance(node.getDocument().getTermVector(), searchTerm );
                node.getDocument().addRelevance( EulerianDistance.transformEulerianDistanceToRelevanceValue( euler ));
                resultDocumentList.add( node.getDocument() );
                nodesOnNextLevel.addAll( node.getChildLeaves() );
                nodeCount++;
            }
        }

        return resultDocumentList;
    }


}
