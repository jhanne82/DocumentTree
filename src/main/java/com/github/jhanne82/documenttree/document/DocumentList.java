package com.github.jhanne82.documenttree.document;


import java.util.TreeSet;


/**
 * List implementation to store a sorted list of a defined number of objects from type <tt>Document</tt>
 * related to the last calculated relevance.
 * In addition to the documents further information related to the search will be stored.
 *
 */
public class DocumentList<T> {



    private final int                size;
    private final TreeSet<DocumentListItem<T>> resultTree;



    /**
     * Constructs an empty list.
     * @param size defines the maximum count of documents which can be stored in this list.
     */
    public DocumentList(int size ) {
        resultTree = new TreeSet<>();
        this.size = size;
    }



    /**
     * Appends the specified element related to the last calculated relevance. When the maximum count of documents
     * is reached, the document will replace a document with a lower relevance value.
     * @param documentToAdd document which should be added to the list
     * @param searchesTillDocument number of documents which were searched before the search reached this document
     * @param stepsInTreeTillDocument number of documents which needs to be considered to reach at this document
     */
    public void add( Document<T> documentToAdd, int searchesTillDocument, int stepsInTreeTillDocument ) {

        // if max count of stored documents is not reach, just add the document
        if( resultTree.size() < size ) {
            resultTree.add( new DocumentListItem<>( documentToAdd, searchesTillDocument, stepsInTreeTillDocument ) );
        } else {

            // if max count is not reach, replace the document from the list with the smallest relevance if this value
            // is smaller than the relevance of the document which should be added to the ResultDocumentList
            DocumentListItem lastStoredResult = resultTree.last();
            if( lastStoredResult.document.getLatestCalculatedRelevance() < documentToAdd.getLatestCalculatedRelevance() ) {
                resultTree.remove( lastStoredResult );
                resultTree.add( new DocumentListItem<>( documentToAdd, searchesTillDocument, stepsInTreeTillDocument ) );
            }
        }
    }



    /**
     * Returns the element with the highest relevance.
     * @return element with the highest relevance.
     */
    public Document<T> getBestResult() {
        return resultTree.first().document;
    }



    /**
     * Returns the number of performed searches on the DocumentTree to reach the Document with the highest relevance.
     *
     * @return the number of performed searches to reach the document marked as best result
     */
    public int numberOfSearchesTillOptimum() {
        return resultTree.first().requiredSearchesTillDocument;
    }



    /**
     * Returns the number of steps which were required to reach the document with the
     * highest relevance within the document tree.
     *
     * @return number of steps needed to reach the document marked as best result
     */
    public int numberOfVisitedNodes() {
        return resultTree.first().requiredStepsInTreeTillDocument;
    }



    /*
     * Wrapper class for the Document and related information which should be stored in DocumentList
     */
    private class DocumentListItem<R>
        implements Comparable<DocumentListItem<R>>{

        int requiredSearchesTillDocument = 0;
        int requiredStepsInTreeTillDocument = 0;
        Document<R> document;


        DocumentListItem(Document<R> document, int requiredSearchesTillDocument, int requiredStepsInTreeTillDocument ) {
            this.document = document;
            this.requiredSearchesTillDocument = requiredSearchesTillDocument;
            this.requiredStepsInTreeTillDocument = requiredStepsInTreeTillDocument;
        }

        @Override
        public int compareTo(DocumentListItem<R> o) {
            if( o == null || o.document == null ) {
                return -1;
            }

            return Double
                    .compare( this.document.getLatestCalculatedRelevance(), o.document.getLatestCalculatedRelevance() );
        }
    }
}
