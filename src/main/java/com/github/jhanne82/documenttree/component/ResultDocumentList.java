package com.github.jhanne82.documenttree.component;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ResultDocumentList<T>
    extends ArrayList<Document<T>> {

    public static final int MAX_RESULTS = 10;


    public ResultDocumentList() {
        super( MAX_RESULTS );
    }


    @Override
    public boolean add(Document<T> document) {

        if( size() < MAX_RESULTS ) {
            super.add( document );
        } else {
            for( int i = 0; i < MAX_RESULTS; i++ ) {
                Document doc = get( i );
                if( document.getAverageRelevance().compareTo( doc.getAverageRelevance() ) >= 0 ) {
                    add( i, document );
                    remove( MAX_RESULTS - 1 );
                    break;
                }
            }

            sort(new Comparator<Document<T>>() {
                @Override
                public int compare(Document<T> o1, Document<T> o2) {
                    return o1.getAverageRelevance().compareTo( o2.getAverageRelevance() );
                }
            });
        }

        return true;
    }


    @Override
    public String toString() {
        return Arrays.toString( this.toArray() );
    }
}
