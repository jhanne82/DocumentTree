package com.github.jhanne82.documenttree.component;


import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.math.BigDecimal;

public class Document <T>
    implements Cloneable {

    private final CircularFifoBuffer relevanceBuffer;
    private final T[] termVector;

    private int   timestampOfLastSearch;



    public Document( T[] termVector ){
        this.relevanceBuffer = new CircularFifoBuffer( 20 );
        this.termVector = termVector;
    }


    public Document( T[] termVector, int bufferSize ){
        this.relevanceBuffer = new CircularFifoBuffer( bufferSize );
        this.termVector = termVector;
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


    @Override
    public Document<T> clone() {

        Document<T> newDocument = new Document<T>( termVector.clone() );
        return newDocument;
    }
}
