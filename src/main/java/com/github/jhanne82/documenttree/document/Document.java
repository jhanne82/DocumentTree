package com.github.jhanne82.documenttree.document;


import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.math.BigDecimal;

public class Document <T>
    implements Cloneable {

    private final CircularFifoBuffer relevanceBuffer;
    private final T[] termVector;
    private final String documentName;

    private int   timestampOfLastSearch;



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

    
    public BigDecimal getAverageRelevance() {

        BigDecimal averageRelevance = BigDecimal.ZERO;

        for ( Object singleRelevance : relevanceBuffer ) {
            averageRelevance.add( ( BigDecimal )singleRelevance );
        }

        averageRelevance = averageRelevance.divide( BigDecimal.valueOf( relevanceBuffer.size() ), BigDecimal.ROUND_HALF_UP );

        return averageRelevance;
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

        return new Document<>( termVector.clone(), documentName );
    }


    @Override
    public String toString() {
        return documentName + " (" + getAverageRelevance() + ")";
    }
}
