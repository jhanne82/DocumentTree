package com.github.jhanne82.documenttree.document;


import java.util.ArrayList;
import java.util.Arrays;

public class ResultDocumentList<T>
    extends ArrayList<Document<T>> {

    private final int maxResults;


    public ResultDocumentList() {
        this( 10 );
    }


    public ResultDocumentList( int maxResult ) {
        super( maxResult );
        this.maxResults = maxResult;
    }


    @Override
    public boolean add(Document<T> documentToAdd) {

        if( size() < maxResults ) {
            super.add( documentToAdd );
        } else {

            if( maxResults == 1 ) {

                Document<T> alreadyStoredDocument = get( 0 );
                if( documentToAdd.getLastCalculatedRelevance() > alreadyStoredDocument.getLastCalculatedRelevance() ) {
                    super.add( documentToAdd );
                }

            } else {
                for( int i = maxResults - 1; i>= 0; i-- ) {
                    Document existingDocument = get( i );
                    if( existingDocument.getLastCalculatedRelevance() < documentToAdd.getLastCalculatedRelevance() ) {
                        remove( existingDocument );
                        super.add( documentToAdd );
                        break;
                    }

                }

                sort( ( o1, o2 ) -> {
                    if( o1.getLastCalculatedRelevance() < o2.getLastCalculatedRelevance() ) {
                        return -1;
                    }
                    if( o1.getLastCalculatedRelevance() == o2.getLastCalculatedRelevance() ) {
                        return 0;
                    }
                    return 1;
                } );
            }
        }



        return true;
    }


    @Override
    public String toString() {
        return Arrays.toString( this.toArray() );
    }
}
