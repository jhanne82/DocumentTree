package com.github.jhanne82.documenttree.component;


import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.math.BigDecimal;

public class Document <T>{

    private final CircularFifoBuffer relevanceBuffer;
    private final T[] termVector;
    private int number;

    private int   timestampOfLastSearch;



    public Document( T[] termVector, int number ){
        this( termVector, 20, number );
    }



    public Document( T[] termVector, int bufferSize, int number ){
        this.relevanceBuffer = new CircularFifoBuffer( bufferSize );
        this.termVector = termVector;
        this.number = number;
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

        averageRelevance = averageRelevance.divide( BigDecimal.valueOf( relevanceBuffer.size() ) );

        return averageRelevance;
    }



    public void addRelevance( BigDecimal relevance ) {
        relevanceBuffer.add( relevance );
    }



    public void clearRelevanceBuffer() {
        relevanceBuffer.clear();
    }


    public int getNumber() {
        return number;
    }
}
