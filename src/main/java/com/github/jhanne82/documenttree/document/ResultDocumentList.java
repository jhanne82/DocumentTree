package com.github.jhanne82.documenttree.document;


import java.util.TreeSet;

public class ResultDocumentList<T> {

    private final int     maxResults;
    private final TreeSet<Document<T>> resultTree;


    public ResultDocumentList( int maxResult ) {
        resultTree = new TreeSet<>();
        this.maxResults = maxResult;
    }


    public void add(Document<T> documentToAdd) {

        if( resultTree.size() < maxResults ) {
            resultTree.add( documentToAdd );
        } else {

            Document lastStoredResult = resultTree.last();
            if( lastStoredResult.getLastCalculatedRelevance() < documentToAdd.getLastCalculatedRelevance() ) {
                resultTree.remove( lastStoredResult );
                resultTree.add( documentToAdd );
            }
        }
    }


    public Document<T> getBestResult() {
        return resultTree.first();
    }




    @Override
    public String toString() {
        return resultTree.toString();
    }
}
