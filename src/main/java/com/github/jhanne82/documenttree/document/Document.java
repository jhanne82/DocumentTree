package com.github.jhanne82.documenttree.document;


import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.jscience.mathematics.number.Real;

public class Document <T>
    implements Cloneable, Comparable<Document<T>> {

    private final CircularFifoBuffer relevanceBuffer;
    //private final T[] termVector;
    private final String documentName;

    private final T[] termList;

    private int   timestampOfLastSearch;



    public Document( T[] termVectorList, String documentName ){
        this( termVectorList, 20, documentName );
    }


    public Document( T[] termVectorList, int bufferSize, String documentName ){
        this.relevanceBuffer = new CircularFifoBuffer( bufferSize );
        this.termList = termVectorList;
        this.documentName = documentName;
    }



    public void setTimestampOfLastSearch( int timestampOfLastSearch ) {
        this.timestampOfLastSearch = timestampOfLastSearch;
    }


    public int getTimestampOfLastSearch() {
        return this.timestampOfLastSearch;
    }


    public T[] getTermList() {
        return this.termList;
    }



    public String getDocumentName() {
        return documentName;
    }


    
    public Real getAverageRelevance() {

        if( relevanceBuffer.isEmpty() ) {
            return Real.ZERO;
        }

        Real averageRelevance = Real.ZERO;

        for ( Object singleRelevance : relevanceBuffer ) {
            averageRelevance = averageRelevance.plus( (Real)singleRelevance );
        }

        averageRelevance = averageRelevance.divide( relevanceBuffer.size() );

        return averageRelevance;
    }


    public Real getLastCalculatedRelevance() {
        if( relevanceBuffer.isEmpty() ) {
            return Real.ZERO;
        }

        return ( Real )relevanceBuffer.toArray()[ relevanceBuffer.size() - 1];
    }



    public void addRelevance( Real relevance ) {
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

        return new Document<>(termList, documentName );
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

        return this.getLastCalculatedRelevance().compareTo( o.getLastCalculatedRelevance() );
    }
}
