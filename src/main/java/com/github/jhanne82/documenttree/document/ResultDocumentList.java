package com.github.jhanne82.documenttree.document;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultDocumentList<T> {

    private final int     maxResults;
    private final List<Document<T>> resultList;


    public ResultDocumentList( int maxResult ) {
        resultList = new ArrayList<>( maxResult );
        this.maxResults = maxResult;
    }


    public boolean add(Document<T> documentToAdd) {

        if( resultList.size() < maxResults ) {
            resultList.add( documentToAdd );
        } else {

            if( maxResults == 1 ) {

                Document<T> alreadyStoredDocument = resultList.get( 0 );
                if( documentToAdd.getLastCalculatedRelevance() > alreadyStoredDocument.getLastCalculatedRelevance() ) {
                    resultList.clear();
                    resultList.add( documentToAdd );
                }

            } else {
                for( int i = maxResults - 1; i>= 0; i-- ) {
                    Document existingDocument = resultList.get( i );
                    if( existingDocument.getLastCalculatedRelevance() < documentToAdd.getLastCalculatedRelevance() ) {
                        resultList.remove( existingDocument );
                        resultList.add( documentToAdd );
                        break;
                    }

                }

                resultList.sort( (o1, o2) -> {
                    if( o1.getLastCalculatedRelevance() < o2.getLastCalculatedRelevance() ) {
                        return -1;
                    }
                    if( o1.getLastCalculatedRelevance() == o2.getLastCalculatedRelevance() ) {
                        return 0;
                    }
                    return 1;
                });

            }
        }
        return true;
    }


    public Document<T> get( int index ) {

        return resultList.get( index );
    }



    public int size() {
        return resultList.size();
    }


    @Override
    public String toString() {
        return Arrays.toString( resultList.toArray() );
    }
}
