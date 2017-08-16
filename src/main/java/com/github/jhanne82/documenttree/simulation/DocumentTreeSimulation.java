package com.github.jhanne82.documenttree.simulation;

import com.github.jhanne82.documenttree.document.Document;
import com.github.jhanne82.documenttree.document.DocumentTree;

import java.util.List;

public abstract class DocumentTreeSimulation <T> {


    
    private Result resultOfGlobalKnowledge = new Result();
    private Result resultOfLocalKnowledge = new Result();



    private DocumentTree<T> documentTreeWithGlobalKnowledge;
    //private DocumentTree<T> documentTreeWithLocalKnowledge;
    private DocumentTree<T> stressReducedDocumentTree;
    private Document<T>[]   optimalDocumentTree;



    public void setDocumentTreeWithGlobalKnowledge( DocumentTree<T> documentTreeWithGlobalKnowledge ) {
        this.documentTreeWithGlobalKnowledge = documentTreeWithGlobalKnowledge;
    }


    /*
    public void setDocumentTreeWithLocalKnowledge( DocumentTree<T> documentTreeWithLocalKnowledge ) {
        this.documentTreeWithLocalKnowledge = documentTreeWithLocalKnowledge;
    } */



    public DocumentTree<T> getStressReducedDocumentTree() {
        return stressReducedDocumentTree;
    }



    public void setStressReducedDocumentTree( DocumentTree<T> stressReducedDocumentTree ) {
        this.stressReducedDocumentTree = stressReducedDocumentTree;
    }



    public Document<T>[] getOptimalDocumentTree() {
        return optimalDocumentTree;
    }



    public void setOptimalDocumentTree( Document<T>[] optimalDocumentTree ) {
        this.optimalDocumentTree = optimalDocumentTree;
    }



    public void runSimulation( List<T[]> searchTermVectorList,
                               SearchType searchType,
                               int limitForLocalKnowledge,
                               int countOfSearchesForRepositioning ) {

        int i = 0;
        for( T[] searchTerm : searchTermVectorList ) {
            search( searchTerm, searchType, limitForLocalKnowledge );
            System.out.println("Repositierung START...");
        //    documentTreeWithLocalKnowledge.repositioning( countOfSearchesForRepositioning );
            documentTreeWithGlobalKnowledge.repositioning( countOfSearchesForRepositioning );
            System.out.println("Repositierung END...");
        }
    }

    private void search( T[] searchTermVector, SearchType searchType, int limitForLocalKnowledge ) {

        System.out.println( "search START...");
        Document bestMatch = searchOnOptimalDocumentTree( searchTermVector );
        switch ( searchType ) {
            case DEPTH_FIRST:
                resultOfGlobalKnowledge.computeHitMissRate( documentTreeWithGlobalKnowledge.depthFirstSearch( 0, searchTermVector ), bestMatch );
          //      documentTreeWithLocalKnowledge.depthFirstSearch( limitForLocalKnowledge, searchTermVector );
                stressReducedDocumentTree.depthFirstSearch( limitForLocalKnowledge, searchTermVector );
                break;
            case BREADTH_FIRST:
                documentTreeWithGlobalKnowledge.breadthFirstSearch( 0, searchTermVector );
            //    documentTreeWithLocalKnowledge.breadthFirstSearch( limitForLocalKnowledge, searchTermVector );
                stressReducedDocumentTree.breadthFirstSearch( limitForLocalKnowledge, searchTermVector );
                break;
            case RANDOM_WALKER:
            default:
                throw new UnsupportedOperationException();
        }


        System.out.println( "search END... ");
    }


    protected abstract Document<T> searchOnOptimalDocumentTree( T[] searchTermVector );


}
