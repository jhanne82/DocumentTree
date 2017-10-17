package com.github.jhanne82.documenttree.document;


import org.apache.commons.collections.buffer.CircularFifoBuffer;

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

        return new Document<>(termList, documentName );
    }


    @Override
    public String toString() {
        return documentName + " (" + getAverageRelevance() + ")";
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
