package com.github.jhanne82.documenttree.utils;


import com.github.jhanne82.documenttree.document.Document;

import java.util.TreeSet;

public class ResultDocumentList<T> {

    private final int     maxResults;
    private final TreeSet<Result<T>> resultTree;


    public ResultDocumentList( int maxResult ) {
        resultTree = new TreeSet<>();
        this.maxResults = maxResult;
    }


    public void add( Document<T> documentToAdd, int searchesTillDocument) {

        if( resultTree.size() < maxResults ) {
            resultTree.add( new Result( documentToAdd, searchesTillDocument ) );
        } else {

            Result lastStoredResult = resultTree.last();
            if( lastStoredResult.document.getLastCalculatedRelevance() < documentToAdd.getLastCalculatedRelevance() ) {
                resultTree.remove( lastStoredResult );
                resultTree.add( new Result( documentToAdd, searchesTillDocument ) );
            }
        }
    }


    public Document<T> getBestResult() {
        return resultTree.first().document;
    }



    public int numberOfSearchesTillOptimum() {
        return resultTree.first().requiredSearchesTillDocument;
    }



    @Override
    public String toString() {
        return resultTree.toString();
    }



    private class Result<T>
        implements Comparable<Result<T>>{

        int requiredSearchesTillDocument = 0;
        Document<T> document;


        Result( Document<T> document, int requiredSearchesTillDocument ) {
            this.document = document;
            this.requiredSearchesTillDocument = requiredSearchesTillDocument;
        }

        @Override
        public int compareTo(Result<T> o) {
            if( o == null || o.document == null ) {
                return -1;
            }

            if( this.document.getLastCalculatedRelevance() < o.document.getLastCalculatedRelevance() ) {
                return -1;
            }
            if( this.document.getLastCalculatedRelevance() == o.document.getLastCalculatedRelevance() ) {
                return 0;
            }
            return 1;
        }
    }
}
