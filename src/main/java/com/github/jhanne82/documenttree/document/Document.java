package com.github.jhanne82.documenttree.document;


import org.apache.commons.collections.buffer.CircularFifoBuffer;


/**
 *  Generic representation of a document.
 *  A document will be defined by a term vector which describes the document and a name.
 *
 * @param <T> the type of the elements of terms describing the document.
 */
public class Document <T>
    implements Cloneable, Comparable<Document<T>> {

    private final CircularFifoBuffer relevanceBuffer;
    private final String             documentName;
    private final T[]                termVector;

    private int                      timestampOfLastSearch;



    /**
     * Constructs a Document object with a defined list of terms and a document name.
     * The size of the relevance buffer is fix with 20.
     * @param termVector defines the elements of the term which describes the document.
     * @param documentName defines the name of the document.
     */
    public Document( T[] termVector, String documentName ){
        this( termVector, 20, documentName );
    }


    public Document( T[] termVector, int bufferSize, String documentName ){
        this.relevanceBuffer = new CircularFifoBuffer( bufferSize );
        this.termVector = termVector;
        this.documentName = documentName;
    }



    public void setTimestampOfLastSearch( int timestampOfLastSearch ) {
        this.timestampOfLastSearch = timestampOfLastSearch;
    }



    public int getTimestampOfLastSearch() {
        return this.timestampOfLastSearch;
    }



    public T[] getTermVector() {
        return this.termVector;
    }



    public String getDocumentName() {
        return documentName;
    }


    
    public double getAverageRelevance() {

        if( relevanceBuffer.isEmpty() ) {
            return 0.0;
        }

        double averageRelevance = 0;

        for ( Object singleRelevance : relevanceBuffer ) {
            averageRelevance += (double)singleRelevance;
        }

        averageRelevance = averageRelevance / relevanceBuffer.size();

        return averageRelevance;
    }



    public double getLastCalculatedRelevance() {
        if( relevanceBuffer.isEmpty() ) {
            return 0;
        }
        return ( double )relevanceBuffer.toArray()[ relevanceBuffer.size() - 1];
    }



    public void addRelevance( double relevance ) {
        relevanceBuffer.add( relevance );
    }



    public void clearRelevanceBuffer() {
        relevanceBuffer.clear();
    }



    public int getCountOfStoredRelevances() {
        return relevanceBuffer.size();
    }



    @Override
    public Document<T> clone() {

        return new Document<>( termVector, documentName );
    }



    @Override
    public String toString() {
        return documentName + " (" + getAverageRelevance() + ")";
    }



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
    


    @Override
    public int compareTo( Document<T> o ) {

        if( o == null ) {
            return -1;
        }

        if( this.getLastCalculatedRelevance() < o.getLastCalculatedRelevance() ) {
            return -1;
        }
        if( this.getLastCalculatedRelevance() == o.getLastCalculatedRelevance() ) {
            return 0;
        }
        return 1;
    }
}
