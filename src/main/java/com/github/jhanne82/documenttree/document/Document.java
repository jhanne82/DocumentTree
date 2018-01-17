package com.github.jhanne82.documenttree.document;


import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.util.Arrays;


/**
 *  Generic representation of a document.
 *  A document will be defined by a term vector which describes the document and a name.
 *
 * @param <T> the type of the elements of terms describing the document.
 */
public class Document <T> {



    private static final int         BUFFER_SIZE = 20;

    private final CircularFifoBuffer relevanceBuffer;
    private final String             documentName;
    private final T[]                termVector;

    private int timestampOfLatestSearch;



    /**
     * Constructs a Document object with a defined list of terms and a document name.
     * The size of the relevance buffer is fix with 20.
     * 
     * @param termVector defines the elements of the term which describes the document.
     * @param documentName defines the name of the document.
     */
    public Document( T[] termVector,String documentName ){
        this.relevanceBuffer = new CircularFifoBuffer( BUFFER_SIZE );
        this.termVector = Arrays.copyOf(termVector, termVector.length);
        this.documentName = documentName;
    }



    /**
     * Sets the timestamp of the latest search on this document.
     *
     * @param timestampOfLatestSearch defines the timestamp
     */
    public void setTimestampOfLatestSearch( int timestampOfLatestSearch ) {
        this.timestampOfLatestSearch = timestampOfLatestSearch;
    }



    /**
     * Returns the timestamp of the latest search on this document.
     *
     * @return timestamp of the latest search
     */
    public int getTimestampOfLatestSearch() {
        return this.timestampOfLatestSearch;
    }



    /**
     * Retuns an array which is the presentation of the term vector describing the document.
     *
     * @return the term vector as array
     */
    public T[] getTermVector() {
        return this.termVector;
    }



    /**
     * Returns the stored name of the document.
     * @return the document name
     */
    public String getDocumentName() {
        return documentName;
    }



    /**
     * Returns the average of all relevance values store in the FIFO buffer.
     *
     * @return the average value over all relevance values
     */
    public double getAverageRelevance() {

        if( relevanceBuffer.isEmpty() ) {
            return 0.0;
        }

        double sumOfRelevance = 0;
        for ( Object singleRelevance : relevanceBuffer ) {
            sumOfRelevance += (double)singleRelevance;
        }

        return sumOfRelevance / relevanceBuffer.size();
    }



    /**
     * Returns the latest relevance value.
     *
     * @return the latest relevance which was added to the FIFO buffer.
     */
    public double getLatestCalculatedRelevance() {
        if( relevanceBuffer.isEmpty() ) {
            return 0;
        }
        return ( double )relevanceBuffer.toArray()[ relevanceBuffer.size() - 1];
    }



    /**
     * Appends the latest calculated relevance to the FIFO buffer containing the relevances.
     *
     * @param relevance value to be appended to the FIFO buffer
     */
    public void addRelevance( double relevance ) {
        relevanceBuffer.add( relevance );
    }



    /**
     * Clears the FIFO buffer which contains the latest calculated relevance values.
     */
    public void clearRelevanceBuffer() {
        relevanceBuffer.clear();
    }



    /**
     * Returns the current size of the ring buffer containing the calculated relevance from the last searches.
     * The size represents the number of past searches for which a relevance was calculated after the document was repositioned.
     * 
     * @return the size of the relevance buffer
     */
    public int getCountOfStoredRelevances() {
        return relevanceBuffer.size();
    }



    /**
     * Indicates whether some other object is "equal to" this one.
     * Equal means for the Document object that the document names are equal.
     *
     * @param   o   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the o
     *          argument; {@code false} otherwise.
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof Document ) ) {
            return false;
        }
        Document<?> document = ( Document<?> )o;

        return documentName != null ? documentName.equals( document.documentName ) : document.documentName == null;
    }
    

}
